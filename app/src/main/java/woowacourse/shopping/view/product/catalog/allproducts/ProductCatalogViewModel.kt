package woowacourse.shopping.view.product.catalog.allproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentlyproducts.RecentlyProductsRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.PagedResult

class ProductCatalogViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentlyProductsRepository: RecentlyProductsRepository,
) : ViewModel() {
    private val pageResultProducts = MutableLiveData<PagedResult<Product>>()

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    private var _productItems: MutableLiveData<List<ProductItem>> = MutableLiveData()
    val productItems: LiveData<List<ProductItem>> = _productItems

    private var _shoppingCartProducts: MutableLiveData<List<ShoppingProduct>> = MutableLiveData()
    val shoppingCartProducts: LiveData<List<ShoppingProduct>> = _shoppingCartProducts

    private var currentPage = 0

    init {
        loadCarts()
    }

    private fun loadCarts() {
        shoppingCartRepository.getAll { result: Result<List<ShoppingProduct>?> ->
            result
                .onSuccess { shoppingProducts: List<ShoppingProduct>? ->
                    _shoppingCartProducts.postValue(shoppingProducts)
                    initLoadProducts(shoppingProducts ?: emptyList())
                }.onFailure {
                }
        }
    }

    private fun initLoadProducts(cartProducts: List<ShoppingProduct>) {
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)
        pageResultProducts.postValue(result)

        recentlyProductsRepository.getAll { result ->
            result
                .onSuccess { recentlyViewedProducts ->
                    _products.postValue(recentlyViewedProducts?.map { it.toProductDomain() } ?: emptyList())
                }.onFailure {
                }
        }

        loadProductItem(result, cartProducts)
    }

    private fun loadProductItem(
        pagedResult: PagedResult<Product>,
        cartProducts: List<ShoppingProduct>,
    ) {
        val productItem: MutableList<ProductItem> =
            pagedResult.items
                .map {
                    ProductItem.CatalogProduct(
                        it,
                        cartProducts.find { p -> p.productId == it.id }?.quantity ?: 0,
                    )
                }.toMutableList()
        if (pagedResult.hasNext) {
            productItem.add(ProductItem.LoadMore)
        }
        _productItems.postValue(productItem)
    }

    fun loadProducts() {
        currentPage++
        val result = productRepository.getPaged(PRODUCT_SIZE_LIMIT, currentPage * PRODUCT_SIZE_LIMIT)

        val newProductList = pageResultProducts.value?.plus(result) ?: result
        pageResultProducts.postValue(newProductList)
        loadProductItem(newProductList, shoppingCartProducts.value ?: emptyList())
    }

    fun addToRecentlyProduct(product: Product) {
        recentlyProductsRepository.insert(product) {}
    }

    fun addToShoppingCart(productId: Long) {
        shoppingCartRepository.increaseProduct(productId) { result: Result<Unit> ->
            result
                .onSuccess {
                    loadCarts()
                }.onFailure {
                }
        }
    }

    fun removeToShoppingCart(productId: Long) {
        shoppingCartRepository.decreaseProduct(productId) { result: Result<Unit> ->
            result
                .onSuccess {
                    loadCarts()
                }.onFailure { }
        }
    }

    companion object {
        private const val PRODUCT_SIZE_LIMIT = 20

        fun provideFactory(
            productRepository: ProductRepository = ShoppingProvider.productRepository,
            shoppingCartRepository: ShoppingCartRepository = ShoppingProvider.shoppingCartRepository,
            recentlyProductsRepository: RecentlyProductsRepository = ShoppingProvider.recentlyProductsRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductCatalogViewModel::class.java)) {
                        return ProductCatalogViewModel(
                            productRepository,
                            shoppingCartRepository,
                            recentlyProductsRepository,
                        ) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
