package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.MockProducts
import woowacourse.shopping.data.MockProducts.mockProducts
import woowacourse.shopping.data.ProductsDataSource

class CatalogViewModel(
    private val dataSource: ProductsDataSource = MockProducts,
) : ViewModel() {
    private val _catalogProducts =
        MutableLiveData<List<ProductUiModel>>(emptyList<ProductUiModel>())
    val catalogProducts: LiveData<List<ProductUiModel>> = _catalogProducts

    val mockProducts get() = dataSource.getProducts()

    val page = MutableLiveData<Int>(INITIAL_PAGE)

    init {
        loadCatalogProducts(PAGE_SIZE)
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        increasePage()
        loadCatalogProducts(pageSize)
    }

    fun isLoadButtonEnabled(): Boolean {
        val totalLoaded = _catalogProducts.value?.size ?: 0
        return totalLoaded < mockProducts.size
    }

    fun increasePage() {
        page.value = page.value?.plus(1)
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val fromIndex = (page.value ?: 0) * pageSize
        val toIndex = minOf(fromIndex + pageSize, mockProducts.size)
        val pagedProducts: List<ProductUiModel> = mockProducts.subList(fromIndex, toIndex)
        _catalogProducts.value = _catalogProducts.value?.plus(pagedProducts)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
