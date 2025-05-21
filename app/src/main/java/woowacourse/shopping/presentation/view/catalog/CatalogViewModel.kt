package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.model.toCatalogItemUiModel
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

    private val _quantityUpdateEvent = MutableSingleLiveData<Pair<Int, Int>>()
    val quantityUpdateEvent: SingleLiveData<Pair<Int, Int>> = _quantityUpdateEvent

    private val limit: Int = 20
    private var page: Int = 0

    init {
        fetchProducts()
    }

    fun increaseProductToCart(
        position: Int,
        productId: Long,
    ) {
        shoppingRepository.addCartItem(productId) { result ->
            result
                .onFailure { _toastEvent.postValue(CatalogMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE) }
                .onSuccess { quantityUpdateSuccessHandle(productId, position) }
        }
    }

    fun decreaseProductFromCart(
        position: Int,
        productId: Long,
    ) {
        shoppingRepository.decreaseCartItemQuantity(productId) { result ->
            result
                .onFailure { _toastEvent.postValue(CatalogMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE) }
                .onSuccess { quantityUpdateSuccessHandle(productId, position) }
        }
    }

    fun fetchProducts() {
        val newOffset = page * limit

        shoppingRepository
            .loadProducts(newOffset, limit)
            .onSuccess { pageableItem -> loadProductsHandleSuccess(pageableItem) }
            .onFailure { _toastEvent.postValue(CatalogMessageEvent.FETCH_PRODUCTS_FAILURE) }
    }

    private fun loadProductsHandleSuccess(pageableItem: PageableItem<Product>) {
        page++

        val existingItems = extractExistingProductItems()
        val newItems = convertToProductItems(pageableItem.items)
        val mergedItems = mergeWithoutDuplicates(existingItems, newItems)
        val finalItems = buildFinalProductList(mergedItems, pageableItem.hasMore)

        _products.postValue(finalItems)
    }

    private fun extractExistingProductItems(): List<CatalogItem.ProductItem> =
        _products.value
            ?.mapNotNull { it as? CatalogItem.ProductItem }
            ?: emptyList()

    private fun convertToProductItems(products: List<Product>): List<CatalogItem.ProductItem> =
        products.map {
            CatalogItem.ProductItem(it.toCatalogItemUiModel())
        }

    private fun mergeWithoutDuplicates(
        oldItems: List<CatalogItem.ProductItem>,
        newItems: List<CatalogItem.ProductItem>,
    ): List<CatalogItem.ProductItem> = (oldItems + newItems).distinctBy { it.product.productId }

    private fun buildFinalProductList(
        productItems: List<CatalogItem.ProductItem>,
        hasMore: Boolean,
    ): List<CatalogItem> =
        buildList {
            addAll(productItems)
            if (hasMore) add(CatalogItem.LoadMoreItem)
        }

    private fun quantityUpdateSuccessHandle(
        productId: Long,
        position: Int,
    ) {
        shoppingRepository.findQuantityByProductId(productId) { result ->
            result
                .onFailure { _toastEvent.postValue(CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE) }
                .onSuccess { _quantityUpdateEvent.postValue(position to it) }
        }
    }

    companion object {
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
