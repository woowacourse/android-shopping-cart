package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem

class CatalogViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<CatalogItem>>()
    val products: LiveData<List<CatalogItem>> = _products

    private val limit: Int = 20
    private var page: Int = 0
    private var offset: Int = 0

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        productRepository.loadProducts(offset, limit) { newProducts, hasMore ->
            page++
            offset = page * limit

            val existingItems = extractExistingProductItems()
            val newItems = convertToProductItems(newProducts)
            val mergedItems = mergeWithoutDuplicates(existingItems, newItems)
            val finalItems = buildFinalProductList(mergedItems, hasMore)

            _products.postValue(finalItems)
        }
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
