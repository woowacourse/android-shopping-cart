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
import woowacourse.shopping.presentation.model.toCatalogItem
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.catalog.event.CatalogMessageEvent

class CatalogViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<CatalogMessageEvent>()
    val toastEvent: SingleLiveData<CatalogMessageEvent> = _toastEvent

    private val _products = MutableLiveData<List<CatalogItem>>()
    val products: LiveData<List<CatalogItem>> = _products

    private val _quantityUpdateEvent = MutableSingleLiveData<Pair<Long, Int>>()
    val quantityUpdateEvent: SingleLiveData<Pair<Long, Int>> = _quantityUpdateEvent

    private val limit: Int = 20
    private var page: Int = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val newOffset = page * limit

        shoppingRepository.loadProducts(newOffset, limit) { result ->
            result.fold(
                onSuccess = { pageableItem -> onProductsLoaded(pageableItem) },
                onFailure = { postFailureEvent(CatalogMessageEvent.FETCH_PRODUCTS_FAILURE) },
            )
        }
    }

    fun refreshProductQuantities() {
        val oldItems = _products.value.orEmpty()
        oldItems.forEach { item ->
            if (item !is CatalogItem.ProductItem) return@forEach

            fetchQuantityAndThen(
                productId = item.product.productId,
                onSuccess = { foundQuantity ->
                    if (foundQuantity != item.product.quantity) {
                        updateProductQuantityInList(item.product.productId, foundQuantity)
                    }
                },
            )
        }
    }

    fun addProductToCart(productId: Long) {
        shoppingRepository.addCartItem(productId, QUANTITY_STEP) { result ->
            result.fold(
                onFailure = { postFailureEvent(CatalogMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE) },
                onSuccess = { updateQuantityAndNotify(productId) },
            )
        }
    }

    fun removeProductFromCart(productId: Long) {
        shoppingRepository.decreaseCartItemQuantity(productId) { result ->
            result.fold(
                onFailure = { postFailureEvent(CatalogMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE) },
                onSuccess = { updateQuantityAndNotify(productId) },
            )
        }
    }

    private fun postFailureEvent(event: CatalogMessageEvent) {
        _toastEvent.postValue(event)
    }

    private fun onProductsLoaded(pageableItem: PageableItem<CartItem>) {
        page++

        val existingItems = getCurrentProductItems()
        val newItems = mapToProductItems(pageableItem.items)
        val mergedItems = mergeUniqueProductItems(existingItems, newItems)
        val finalItems = buildCatalogItemList(mergedItems, pageableItem.hasMore)

        _products.postValue(finalItems)
    }

    private fun getCurrentProductItems(): List<CatalogItem.ProductItem> =
        _products.value?.mapNotNull { it as? CatalogItem.ProductItem }.orEmpty()

    private fun mapToProductItems(products: List<CartItem>): List<CatalogItem.ProductItem> =
        products.map {
            val isOpenQuantitySelector = isOpenQuantitySelector(it.quantity)
            val catalogItem = it.toCatalogItem(isOpenQuantitySelector)
            CatalogItem.ProductItem(catalogItem)
        }

    private fun mergeUniqueProductItems(
        oldItems: List<CatalogItem.ProductItem>,
        newItems: List<CatalogItem.ProductItem>,
    ): List<CatalogItem.ProductItem> = (oldItems + newItems).distinctBy { it.product.productId }

    private fun buildCatalogItemList(
        productItems: List<CatalogItem.ProductItem>,
        hasMore: Boolean,
    ): List<CatalogItem> =
        buildList {
            addAll(productItems)
            if (hasMore) add(CatalogItem.LoadMoreItem)
        }

    private fun updateQuantityAndNotify(productId: Long) {
        fetchQuantityAndThen(
            productId = productId,
            onSuccess = { updatedQuantity ->
                _quantityUpdateEvent.postValue(productId to updatedQuantity)
                updateProductQuantityInList(productId, updatedQuantity)
            },
        )
    }

    private fun updateProductQuantityInList(
        productId: Long,
        updatedQuantity: Int,
    ) {
        val oldItems = _products.value.orEmpty()
        val updatedItems =
            oldItems.map { item ->
                if (item !is CatalogItem.ProductItem || item.product.productId != productId) return@map item

                val newItem =
                    item.product.copy(
                        quantity = updatedQuantity,
                        isOpenQuantitySelector = isOpenQuantitySelector(updatedQuantity),
                    )
                CatalogItem.ProductItem(newItem)
            }

        _products.postValue(updatedItems)
    }

    private fun isOpenQuantitySelector(quantity: Int): Boolean = quantity > OPEN_QUANTITY_SELECTOR_BASE_VALUE

    private fun fetchQuantityAndThen(
        productId: Long,
        onSuccess: (Int) -> Unit,
        onFailureEvent: CatalogMessageEvent = CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE,
    ) {
        shoppingRepository.findQuantityByProductId(productId) { result ->
            result.fold(
                onSuccess = onSuccess,
                onFailure = { postFailureEvent(onFailureEvent) },
            )
        }
    }

    companion object {
        private const val OPEN_QUANTITY_SELECTOR_BASE_VALUE = 0
        private const val QUANTITY_STEP = 1

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val repository = RepositoryProvider.shoppingRepository
                    return CatalogViewModel(repository) as T
                }
            }
    }
}
