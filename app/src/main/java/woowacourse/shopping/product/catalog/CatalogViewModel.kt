package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ProductDatabase
import woowacourse.shopping.data.ProductsDataSource

class CatalogViewModel(
    private val dataSource: ProductsDataSource = ProductDatabase,
) : ViewModel() {
    private val _catalogProducts =
        MutableLiveData<List<ProductUiModel>>(emptyList<ProductUiModel>())
    val catalogProducts: LiveData<List<ProductUiModel>> = _catalogProducts

    val allProductsSize get() = dataSource.getAllProductsSize()

    val page = MutableLiveData<Int>(INITIAL_PAGE)

    private val _updatedItem = MutableLiveData<ProductUiModel>()
    val updatedItem: LiveData<ProductUiModel> = _updatedItem

    init {
        loadCatalogProducts(PAGE_SIZE)
    }

    fun increaseQuantity(product: ProductUiModel) {
        val item = dataSource.changeProductQuantity(product, 1)
        _updatedItem.value = item
    }

    fun decreaseQuantity(product: ProductUiModel) {
        val item = dataSource.changeProductQuantity(product, -1)
        _updatedItem.value = item
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        increasePage()
        loadCatalogProducts(pageSize)
    }

    fun isLoadButtonEnabled(): Boolean {
        val totalLoaded = _catalogProducts.value?.size ?: 0
        return totalLoaded < allProductsSize
    }

    fun increasePage() {
        page.value = page.value?.plus(1)
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val currentPage = page.value ?: INITIAL_PAGE
        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, allProductsSize)
        if (startIndex >= allProductsSize) {
            _catalogProducts.value = emptyList()
            return
        }
        val pagedProducts: List<ProductUiModel> =
            dataSource.getProductsInRange(startIndex, endIndex)

        _catalogProducts.value = _catalogProducts.value?.plus(pagedProducts)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
