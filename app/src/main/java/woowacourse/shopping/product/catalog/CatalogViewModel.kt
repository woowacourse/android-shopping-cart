package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ProductDatabase
import woowacourse.shopping.data.ProductsDataSource

class CatalogViewModel(
    private val dataSource: ProductsDataSource = ProductDatabase,
) : ViewModel() {
    val allProductsSize get() = dataSource.getAllProductsSize()

    private val _catalogItems =
        MutableLiveData<List<CatalogItem>>(emptyList<CatalogItem>())
    val catalogItems: LiveData<List<CatalogItem>> = _catalogItems

    private val _page = MutableLiveData<Int>(INITIAL_PAGE)
    val page: LiveData<Int> = _page

    private val _updatedItem = MutableLiveData<ProductUiModel>()
    val updatedItem: LiveData<ProductUiModel> = _updatedItem

    private val _cartItemSize = MutableLiveData<Int>(0)
    val cartItemSize: LiveData<Int> = _cartItemSize

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

    fun increasePage() {
        _page.value = _page.value?.plus(1)
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val currentPage = page.value ?: INITIAL_PAGE
        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, allProductsSize)
        if (startIndex >= allProductsSize) {
            _catalogItems.value = emptyList()
            return
        }
        val pagedProducts: List<CatalogItem> =
            dataSource.getProductsInRange(startIndex, endIndex).map { CatalogItem.ProductItem(it) }
        val items: List<CatalogItem> =
            if (pagedProducts.size == PAGE_SIZE && (PAGE_SIZE * currentPage) + pagedProducts.size < allProductsSize) {
                pagedProducts + CatalogItem.LoadMoreButtonItem
            } else {
                pagedProducts
            }

        _catalogItems.value = items
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
