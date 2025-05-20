package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ProductsDataSource

class CatalogViewModel(
    private val dataSource: ProductsDataSource,
) : ViewModel() {
    private val _catalogProducts =
        MutableLiveData<List<ProductUiModel>>(emptyList<ProductUiModel>())
    val catalogProducts: LiveData<List<ProductUiModel>> = _catalogProducts

    private val _page = MutableLiveData<Int>(INITIAL_PAGE)
    val page: LiveData<Int> = _page

    init {
        loadCatalogProducts(PAGE_SIZE)
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        increasePage()
        loadCatalogProducts(pageSize)
    }

    fun isLoadButtonEnabled(): Boolean {
        val totalLoaded = _catalogProducts.value?.size ?: 0
        return totalLoaded < dataSource.getProductsSize()
    }

    fun increasePage() {
        _page.value = _page.value?.plus(1)
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val fromIndex = (page.value ?: 0) * pageSize
        val toIndex = minOf(fromIndex + pageSize, dataSource.getProductsSize())

        val pagedProducts = dataSource.getSubListedProducts(fromIndex, toIndex)
        _catalogProducts.value = _catalogProducts.value?.plus(pagedProducts)
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
