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

    private var page = INITIAL_PAGE

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

    fun addToCart(product: ProductUiModel) {
        cartProductRepository.insertCartProduct(product.copy(quantity = 1).toEntity()) {
            cartProductRepository.getProduct(product.id) { addedProduct ->
                _updatedItem.postValue(addedProduct.toUiModel())
                loadCartItemSize()
            }
        }
    }

    fun increaseQuantity(productId: Int) {
        cartProductRepository.updateProductQuantity(productId, SIZE_ONE) {
            cartProductRepository.getProduct(productId) { product ->
                _updatedItem.postValue(product.toUiModel())
                loadCartItemSize()
            }
        }
    }

    fun decreaseQuantity(productId: Int) {
        cartProductRepository.getProductQuantity(productId) { quantity ->
            if (quantity == SIZE_ONE) {
                cartProductRepository.deleteCartProduct(productId) {
                    val item: CatalogItem =
                        catalogItems.value?.first { (it as ProductItem).productItem.id == productId }
                            ?: return@deleteCartProduct
                    _updatedItem.postValue((item as ProductItem).productItem.copy(quantity = 0))
                }
            } else {
                cartProductRepository.updateProductQuantity(productId, -SIZE_ONE) {
                    cartProductRepository.getProduct(productId) { product ->
                        _updatedItem.postValue(product.toUiModel())
                    }
                }
            }
            loadCartItemSize()
        }
    }

    fun loadNextCatalogProducts() {
        page++

        catalogProductRepository.getAllProductsSize { size ->
            val startIndex = page * PAGE_SIZE
            val endIndex = minOf(startIndex + PAGE_SIZE, size)

            loadCatalog(startIndex, endIndex, size)
        }
    }

    fun loadCatalogUntilCurrentPage() {
        _catalogItems.value = emptyList()

        catalogProductRepository.getAllProductsSize { size ->
            val startIndex = 0
            val endIndex = minOf((page + 1) * PAGE_SIZE, size)

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

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
        private const val SIZE_ONE = 1
    }
}
