package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.data.mapper.toUiModel
import woowacourse.shopping.data.repository.CartProductRepository
import woowacourse.shopping.data.repository.CatalogProductRepository
import woowacourse.shopping.data.repository.RecentlyViewedProductRepository
import woowacourse.shopping.product.catalog.CatalogItem.ProductItem

class CatalogViewModel(
    private val catalogProductRepository: CatalogProductRepository,
    private val cartProductRepository: CartProductRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
) : ViewModel() {
    private val _catalogItems =
        MutableLiveData<List<CatalogItem>>(emptyList<CatalogItem>())
    val catalogItems: LiveData<List<CatalogItem>> = _catalogItems

    private val _page = MutableLiveData<Int>(INITIAL_PAGE)
    val page: LiveData<Int> = _page

    private val _updatedItem = MutableLiveData<ProductUiModel>()
    val updatedItem: LiveData<ProductUiModel> = _updatedItem

    private val _cartItemSize = MutableLiveData<Int>(0)
    val cartItemSize: LiveData<Int> = _cartItemSize

    private val _recentlyViewedProducts =
        MutableLiveData<List<ProductUiModel>>(emptyList<ProductUiModel>())
    val recentlyViewedProducts: LiveData<List<ProductUiModel>> = _recentlyViewedProducts

    init {
        catalogProductRepository.getAllProductsSize { allProductsSize ->
            loadCatalog(0, PAGE_SIZE, allProductsSize)
        }
    }

    fun increaseQuantity(product: ProductUiModel) {
        cartProductRepository.updateProduct(product.toEntity(), 1) { updatedProduct ->
            _updatedItem.postValue(updatedProduct?.toUiModel())
        }
        loadCartItemSize()
    }

    fun decreaseQuantity(product: ProductUiModel) {
        cartProductRepository.getProductQuantity(product.id) { quantity ->
            if (quantity == 1) {
                cartProductRepository.deleteCartProduct(product.toEntity().copy(quantity = 1))
                _updatedItem.postValue(product.copy(quantity = 0))
            } else {
                cartProductRepository.updateProduct(product.toEntity(), -1) { updatedProduct ->
                    _updatedItem.postValue(updatedProduct?.toUiModel())
                }
            }
        }
        loadCartItemSize()
    }

    fun loadNextCatalogProducts() {
        increasePage()
        val currentPage = page.value ?: 0

        catalogProductRepository.getAllProductsSize { size ->
            val startIndex = currentPage * PAGE_SIZE
            val endIndex = minOf(startIndex + PAGE_SIZE, size)

            loadCatalog(startIndex, endIndex, size)
        }
    }

    fun loadCatalogUntilCurrentPage() {
        _catalogItems.value = emptyList()
        val currentPage = page.value ?: 0

        catalogProductRepository.getAllProductsSize { size ->
            val startIndex = 0
            val endIndex = minOf((currentPage + 1) * PAGE_SIZE, size)

            loadCatalog(startIndex, endIndex, size)
        }
    }

    fun loadCatalog(
        startIndex: Int,
        endIndex: Int,
        allProductsSize: Int,
    ) {
        catalogProductRepository.getProductsInRange(startIndex, endIndex) { pagedProducts ->

            cartProductRepository.getCartProductsInRange(startIndex, endIndex) { cartProducts ->
                val cartProductMap = cartProducts.associateBy { it.uid }

                val mergedProducts =
                    pagedProducts.map { product ->
                        val cartProduct = cartProductMap[product.id]
                        if (cartProduct != null) product.copy(quantity = cartProduct.quantity) else product
                    }

                val items = mergedProducts.map { ProductItem(it) }
                val prevItems =
                    _catalogItems.value.orEmpty().filterNot { it is CatalogItem.LoadMoreButtonItem }
                val hasNextPage = endIndex < allProductsSize

                val updatedItems =
                    if (hasNextPage) {
                        prevItems + items + CatalogItem.LoadMoreButtonItem
                    } else {
                        prevItems + items
                    }

                _catalogItems.postValue(updatedItems)
            }
        }
    }

    fun loadCartItemSize() {
        cartProductRepository.getCartItemSize { size ->
            _cartItemSize.postValue(size)
        }
    }

    fun loadRecentlyViewedProducts() {
        recentlyViewedProductRepository.getRecentlyViewedProducts { products ->
            _recentlyViewedProducts.postValue(products.map { it.toUiModel() })
        }
    }

    private fun increasePage() {
        _page.value = _page.value?.plus(1)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
    }
}
