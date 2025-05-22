package woowacourse.shopping.product.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CatalogDataSource
import woowacourse.shopping.data.CatalogDatabase
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.data.repository.CartProductRepository

class CatalogViewModel(
    private val catalogDataSource: CatalogDataSource = CatalogDatabase,
    private val cartProductRepository: CartProductRepository,
) : ViewModel() {
    val allProductsSize get() = catalogDataSource.getAllProductsSize()

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
        val item = catalogDataSource.changeProductQuantity(product, 1)
        _updatedItem.value = item
        cartProductRepository.updateProduct(item.toEntity())
        loadCartItemSize()
    }

    fun decreaseQuantity(product: ProductUiModel) {
        val item = catalogDataSource.changeProductQuantity(product, -1)
        _updatedItem.value = item
        Log.d("UPDATED", "$item")
        if (item.quantity == 0) {
            Log.d("DELETED", "Deleting target is $product")
            cartProductRepository.deleteCartProduct(product.toEntity())
        } else {
            cartProductRepository.updateProduct(item.toEntity())
        }
        loadCartItemSize()
    }

    fun loadCartItemSize() {
        cartProductRepository.getCartItemSize { size ->
            _cartItemSize.postValue(size)
        }
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        increasePage()
        loadCatalogProducts(pageSize)
    }

    fun increasePage() {
        _page.value = _page.value?.plus(1)
    }

    fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val currentPage = page.value ?: INITIAL_PAGE
        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, allProductsSize)
        if (startIndex >= allProductsSize) {
            _catalogItems.value = emptyList()
            return
        }
        val pagedProducts: List<CatalogItem> =
            catalogDataSource
                .getProductsInRange(startIndex, endIndex)
                .map { CatalogItem.ProductItem(it) }
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
