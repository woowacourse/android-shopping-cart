package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.catalog.adapter.model.CatalogItem
import woowacourse.shopping.presentation.view.catalog.adapter.model.toCatalogProductItem
import woowacourse.shopping.presentation.view.catalog.event.CatalogMessageEvent

class CatalogViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val recentRepository: RecentProductRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<CatalogMessageEvent>()
    val toastEvent: SingleLiveData<CatalogMessageEvent> = _toastEvent

    private val _products = MutableLiveData<List<CatalogItem>>()
    val products: LiveData<List<CatalogItem>> = _products

    private val _cartItemCount = MutableLiveData<Int>()
    val cartItemCount: LiveData<Int> = _cartItemCount

    private var page = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val offset = page * PRODUCT_LOAD_LIMIT

        shoppingRepository.loadProducts(offset, PRODUCT_LOAD_LIMIT) { result ->
            result
                .onSuccess { onProductPageLoaded(it) }
                .onFailure { emitToastMessage(CatalogMessageEvent.FETCH_PRODUCTS_FAILURE) }
        }
    }

    fun addProductToCart(productId: Long) {
        shoppingRepository.addCartItem(productId, QUANTITY_STEP) { result ->
            result
                .onSuccess { refreshProductQuantity(productId) }
                .onFailure { emitToastMessage(CatalogMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE) }
        }
    }

    fun removeProductFromCart(productId: Long) {
        shoppingRepository.decreaseCartItemQuantity(productId) { result ->
            result
                .onSuccess { refreshProductQuantity(productId) }
                .onFailure { emitToastMessage(CatalogMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE) }
        }
    }

    fun refreshProducts() {
        if (_products.value.isNullOrEmpty()) return

        fetchRecentProducts { recentProductItem ->
            fetchCartItemsAndUpdateCatalog(recentProductItem)
        }
    }

    private fun onProductPageLoaded(pageable: PageableItem<CartItem>) {
        page++

        val currentItems = getCurrentCatalogProductItems()
        val newItems = mapCartItemsToCatalogItems(pageable.items)
        val merged = mergeCatalogItems(currentItems, newItems)
        val catalogWithLoadMore = appendLoadMoreItem(merged, pageable.hasMore)

        fetchRecentProducts { recentProductsItem ->
            val finalCatalog = prependRecentProductsItem(recentProductsItem, catalogWithLoadMore)
            _products.postValue(finalCatalog)
        }
    }

    private fun fetchRecentProducts(onRecentProductsFetched: (CatalogItem.RecentProducts) -> Unit) {
        recentRepository.getRecentProducts(RECENT_PRODUCT_LIMIT) { result ->
            result
                .onFailure { emitToastMessage(CatalogMessageEvent.FETCH_RECENT_PRODUCT_FAILURE) }
                .onSuccess {
                    val uiModels = it.map { product -> product.toUiModel() }
                    val recentProductsItem = CatalogItem.RecentProducts(uiModels)
                    onRecentProductsFetched(recentProductsItem)
                }
        }
    }

    private fun fetchCartItemsAndUpdateCatalog(recentProductsItem: CatalogItem.RecentProducts) {
        shoppingRepository.getAll { result ->
            result
                .onFailure { emitToastMessage(CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE) }
                .onSuccess { cartItems ->
                    val currentItems = _products.value.orEmpty()
                    val updatedItems =
                        currentItems.map { item -> updateItemQuantity(cartItems, item) }
                    val finalCatalog = prependRecentProductsItem(recentProductsItem, updatedItems)

                    _products.postValue(finalCatalog)
                    fetchCartItemCount()
                }
        }
    }

    private fun refreshProductQuantity(productId: Long) {
        shoppingRepository.findQuantityByProductId(productId) { result ->
            result.onSuccess { newQuantity -> updateProductQuantityInList(productId, newQuantity) }
            result.onFailure { emitToastMessage(CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE) }
        }
    }

    private fun updateProductQuantityInList(
        productId: Long,
        quantity: Int,
    ) {
        val currentProducts = _products.value.orEmpty()
        val updated =
            currentProducts.map { item ->
                if (item !is CatalogItem.ProductItem || item.productId != productId) return@map item
                item.copy(quantity = quantity)
            }

        _products.postValue(updated)
        fetchCartItemCount()
    }

    private fun fetchCartItemCount() {
        shoppingRepository.getTotalQuantity { result ->
            result
                .onSuccess { _cartItemCount.postValue(it) }
                .onFailure { emitToastMessage(CatalogMessageEvent.FETCH_CART_ITEM_COUNT_FAILURE) }
        }
    }

    private fun getCurrentCatalogProductItems(): List<CatalogItem.ProductItem> =
        _products.value.orEmpty().filterIsInstance<CatalogItem.ProductItem>()

    private fun mapCartItemsToCatalogItems(items: List<CartItem>): List<CatalogItem.ProductItem> = items.map { it.toCatalogProductItem() }

    private fun mergeCatalogItems(
        oldItems: List<CatalogItem.ProductItem>,
        newItems: List<CatalogItem.ProductItem>,
    ): List<CatalogItem.ProductItem> = (oldItems + newItems).distinctBy { it.productId }

    private fun appendLoadMoreItem(
        products: List<CatalogItem.ProductItem>,
        hasMore: Boolean,
    ): List<CatalogItem> =
        buildList {
            addAll(products)
            if (hasMore) add(CatalogItem.LoadMoreItem)
        }

    private fun prependRecentProductsItem(
        recentProducts: CatalogItem.RecentProducts?,
        items: List<CatalogItem>,
    ): List<CatalogItem> {
        val cleanedItems = items.filterNot { it is CatalogItem.RecentProducts }
        return buildList {
            if (recentProducts != null && recentProducts.products.isNotEmpty()) add(recentProducts)
            addAll(cleanedItems)
        }
    }

    private fun updateItemQuantity(
        cartItems: List<CartItem>,
        item: CatalogItem,
    ): CatalogItem {
        if (item !is CatalogItem.ProductItem) return item

        val matchedCartItem = cartItems.firstOrNull { it.product.id == item.productId }
        val quantity = matchedCartItem?.quantity ?: DEFAULT_QUANTITY

        return item.copy(quantity = quantity)
    }

    private fun emitToastMessage(event: CatalogMessageEvent) {
        _toastEvent.postValue(event)
    }

    companion object {
        private const val DEFAULT_QUANTITY = 0
        private const val QUANTITY_STEP = 1
        private const val PRODUCT_LOAD_LIMIT = 20
        private const val RECENT_PRODUCT_LIMIT = 10

        val Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val shoppingRepository = RepositoryProvider.shoppingRepository
                    val recentRepository = RepositoryProvider.recentProductRepository
                    return CatalogViewModel(shoppingRepository, recentRepository) as T
                }
            }
    }
}
