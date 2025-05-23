package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.catalog.adapter.toCatalogProductItem
import woowacourse.shopping.presentation.view.catalog.event.CatalogMessageEvent

class CatalogViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<CatalogMessageEvent>()
    val toastEvent: SingleLiveData<CatalogMessageEvent> = _toastEvent

    private val _products = MutableLiveData<List<CatalogItem>>()
    val products: LiveData<List<CatalogItem>> = _products

    private val _cartItemCount = MutableLiveData<Int>()
    val cartItemCount: LiveData<Int> = _cartItemCount

    private val limit = 20
    private var page = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val offset = page * limit

        shoppingRepository.loadProducts(offset, limit) { result ->
            result.fold(
                onSuccess = ::handleProductPageLoaded,
                onFailure = { postFailureEvent(CatalogMessageEvent.FETCH_PRODUCTS_FAILURE) },
            )
        }
    }

    fun fetchCartInfoChanged() {
        if (_products.value.isNullOrEmpty()) return

        shoppingRepository.getAll { result ->
            result.fold(
                onSuccess = ::updateQuantitiesWithCart,
                onFailure = { postFailureEvent(CatalogMessageEvent.FETCH_PRODUCTS_FAILURE) },
            )
        }
    }

    fun addProductToCart(productId: Long) {
        shoppingRepository.addCartItem(productId, QUANTITY_STEP) { result ->
            result.fold(
                onSuccess = { refreshQuantity(productId) },
                onFailure = { postFailureEvent(CatalogMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE) },
            )
        }
    }

    fun removeProductFromCart(productId: Long) {
        shoppingRepository.decreaseCartItemQuantity(productId) { result ->
            result.fold(
                onSuccess = { refreshQuantity(productId) },
                onFailure = { postFailureEvent(CatalogMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE) },
            )
        }
    }

    private fun fetchCartItemCount() {
        shoppingRepository.getTotalQuantity { result ->
            result.fold(
                onSuccess = { _cartItemCount.postValue(it) },
                onFailure = { postFailureEvent(CatalogMessageEvent.FETCH_CART_ITEM_COUNT_FAILURE) },
            )
        }
    }

    private fun handleProductPageLoaded(pageable: PageableItem<CartItem>) {
        page++
        val currentItems = getCurrentProductItems()
        val newItems = mapToProductItems(pageable.items)
        val merged = mergeProducts(currentItems, newItems)
        val built = buildCatalogItems(merged, pageable.hasMore)
        _products.postValue(built)

        fetchCartItemCount()
    }

    private fun getCurrentProductItems(): List<CatalogItem.ProductItem> =
        _products.value.orEmpty().filterIsInstance<CatalogItem.ProductItem>()

    private fun mapToProductItems(items: List<CartItem>): List<CatalogItem.ProductItem> =
        items.map { cartItem -> cartItem.toCatalogProductItem() }

    private fun mergeProducts(
        oldItems: List<CatalogItem.ProductItem>,
        newItems: List<CatalogItem.ProductItem>,
    ): List<CatalogItem.ProductItem> = (oldItems + newItems).distinctBy { it.productId }

    private fun buildCatalogItems(
        products: List<CatalogItem.ProductItem>,
        hasMore: Boolean,
    ): List<CatalogItem> =
        buildList {
            addAll(products)
            if (hasMore) add(CatalogItem.LoadMoreItem)
        }

    private fun updateQuantitiesWithCart(cartItems: List<CartItem>) {
        val currentItems = _products.value.orEmpty()
        val updated =
            currentItems.map { item ->
                if (item !is CatalogItem.ProductItem) return@map item

                updateQuantityFromCart(item, cartItems)
            }

        _products.postValue(updated)
        fetchCartItemCount()
    }

    private fun updateQuantityFromCart(
        item: CatalogItem.ProductItem,
        cartItems: List<CartItem>,
    ): CatalogItem.ProductItem {
        val quantity = cartItems.find { it.product.id == item.productId }?.quantity ?: 0
        return item.copy(quantity = quantity)
    }

    private fun refreshQuantity(productId: Long) {
        shoppingRepository.findQuantityByProductId(productId) { result ->
            result.fold(
                onSuccess = { updateQuantityInList(productId, it) },
                onFailure = { postFailureEvent(CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE) },
            )
        }
    }

    private fun updateQuantityInList(
        productId: Long,
        quantity: Int,
    ) {
        val currentItems = _products.value.orEmpty()
        val updated =
            currentItems.map { item ->
                if (item !is CatalogItem.ProductItem || item.productId != productId) return@map item
                item.copy(quantity = quantity)
            }
        _products.postValue(updated)
        fetchCartItemCount()
    }

    private fun postFailureEvent(event: CatalogMessageEvent) {
        _toastEvent.postValue(event)
    }

    companion object {
        private const val QUANTITY_STEP = 1

        val Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T = CatalogViewModel(RepositoryProvider.shoppingRepository) as T
            }
    }
}
