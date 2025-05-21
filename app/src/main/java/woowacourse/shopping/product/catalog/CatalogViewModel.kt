package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ProductsDataSource

class CatalogViewModel(
    private val dataSource: ProductsDataSource,
) : ViewModel() {
    private val _pagingData = MutableLiveData<PagingData>()
    val pagingData: LiveData<PagingData> = _pagingData

    private var currentPage = INITIAL_PAGE
    private var loadedProducts = emptyList<ProductUiModel>()

    val page: Int get() = currentPage
    val products: List<ProductUiModel> get() = loadedProducts

    init {
        loadCatalogProducts()
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        currentPage += 1
        loadCatalogProducts(pageSize)
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val fromIndex = currentPage * pageSize
        val toIndex = minOf(fromIndex + pageSize, dataSource.getProductsSize())
        val pagedProducts = dataSource.getSubListedProducts(fromIndex, toIndex)

        loadedProducts += pagedProducts
        val hasNext = loadedProducts.size < dataSource.getProductsSize()

        _pagingData.value =
            PagingData(
                products = loadedProducts,
                hasNext = hasNext,
            )
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0

        fun factory(dataSource: ProductsDataSource): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
                        return CatalogViewModel(dataSource) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
