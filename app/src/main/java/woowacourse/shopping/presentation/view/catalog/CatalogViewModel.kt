package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.catalog.event.CatalogMessageEvent

class CatalogViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<CatalogMessageEvent>()
    val toastEvent: SingleLiveData<CatalogMessageEvent> = _toastEvent

    private val _products = MutableLiveData<List<CatalogItem>>()
    val products: LiveData<List<CatalogItem>> = _products

    private val limit: Int = 20
    private var page: Int = 0

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        val newOffset = page * limit

        productRepository
            .loadProducts(newOffset, limit)
            .onSuccess { pageableItem -> loadProductsSuccessHandler(pageableItem) }
            .onFailure { _toastEvent.postValue(CatalogMessageEvent.FETCH_PRODUCTS_FAILURE) }
    }

    private fun loadProductsSuccessHandler(pageableItem: PageableItem<Product>) {
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
            CatalogItem.ProductItem(it.toUiModel())
        }

    private fun mergeWithoutDuplicates(
        oldItems: List<CatalogItem.ProductItem>,
        newItems: List<CatalogItem.ProductItem>,
    ): List<CatalogItem.ProductItem> = (oldItems + newItems).distinctBy { it.product.id }

    private fun buildFinalProductList(
        productItems: List<CatalogItem.ProductItem>,
        hasMore: Boolean,
    ): List<CatalogItem> =
        buildList {
            addAll(productItems)
            if (hasMore) add(CatalogItem.LoadMoreItem)
        }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val repository = RepositoryProvider.productRepository
                    return CatalogViewModel(repository) as T
                }
            }
    }
}
