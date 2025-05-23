package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CatalogDataSource
import woowacourse.shopping.data.CatalogDatabase
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.data.repository.CartProductRepository
import woowacourse.shopping.product.catalog.CatalogItem.ProductItem

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
        if (item.quantity == 0) {
            cartProductRepository.deleteCartProduct(product.toEntity())
        } else {
            cartProductRepository.updateProduct(item.toEntity())
        }
        loadCartItemSize()
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        increasePage()
        loadCatalogProducts(pageSize)
    }

    fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val currentPage = page.value ?: INITIAL_PAGE
        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, allProductsSize)

        if (startIndex >= allProductsSize) return

        val pagedProducts: List<ProductUiModel> =
            catalogDataSource.getProductsInRange(startIndex, endIndex)

        cartProductRepository.getCartProductsInRange(startIndex, endIndex) { cartProducts ->

            val cartProductMap = cartProducts.associateBy { it.uid }

            val mergedProducts: List<ProductUiModel> =
                pagedProducts.map { product ->
                    val cartProduct = cartProductMap[product.id]
                    if (cartProduct != null && product.id == cartProduct.uid) {
                        product.copy(quantity = cartProduct.quantity)
                    } else {
                        product
                    }
                }

            synchronized(CatalogDatabase.dummyProducts) {
                mergedProducts.forEachIndexed { index, merged ->
                    val dbIndex = CatalogDatabase.dummyProducts.indexOfFirst { it.id == merged.id }
                    if (dbIndex != -1) {
                        CatalogDatabase.dummyProducts[dbIndex] = merged
                    }
                }
            }

            val mergedCatalogItems = mergedProducts.map { CatalogItem.ProductItem(it) }

            val hasNextPage =
                mergedProducts.size == pageSize &&
                    (pageSize * currentPage) + mergedProducts.size < allProductsSize

            val currentItems =
                _catalogItems.value
                    .orEmpty()
                    .filterNot { it is CatalogItem.LoadMoreButtonItem } // 기존 버튼 제거

            val updatedItems =
                if (hasNextPage) {
                    currentItems + mergedCatalogItems + CatalogItem.LoadMoreButtonItem
                } else {
                    currentItems + mergedCatalogItems
                }

            _catalogItems.postValue(updatedItems)
        }
    }

    fun updateAfterCheckingDiff() {
        val currentItems = catalogItems.value ?: emptyList()
        if (currentItems.isEmpty()) return

        val productItems = currentItems.filterIsInstance<ProductItem>()
        val size = productItems.size

        cartProductRepository.getCartProductsInRange(0, size) { cartProducts ->
            val cartProductMap = cartProducts.associateBy { it.uid }

            val mergedProducts =
                productItems.map { productItem ->
                    val product = productItem.productItem
                    val updatedQuantity = cartProductMap[product.id]?.quantity ?: 0
                    ProductItem(product.copy(quantity = updatedQuantity))
                }

            val hasLoadMore = currentItems.lastOrNull() is CatalogItem.LoadMoreButtonItem
            val finalItems =
                if (hasLoadMore) {
                    mergedProducts + CatalogItem.LoadMoreButtonItem
                } else {
                    mergedProducts
                }

            _catalogItems.postValue(finalItems)
        }
    }

    private fun increasePage() {
        _page.value = _page.value?.plus(1)
    }

    private fun loadCartItemSize() {
        cartProductRepository.getCartItemSize { size ->
            _cartItemSize.postValue(size)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
