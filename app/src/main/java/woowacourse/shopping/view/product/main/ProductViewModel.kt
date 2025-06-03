package woowacourse.shopping.view.product.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.data.repository.products.catalog.ProductRepository
import woowacourse.shopping.data.repository.products.recentlyviewed.RecentlyViewedRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.main.adapter.ViewItems
import woowacourse.shopping.view.product.main.adapter.ViewItems.Divider
import woowacourse.shopping.view.product.main.adapter.ViewItems.Products
import woowacourse.shopping.view.product.main.adapter.ViewItems.RecentlyViewedProducts
import woowacourse.shopping.view.product.main.adapter.ViewItems.ShowMore

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedRepository: RecentlyViewedRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ViewItems>>(emptyList())
    val products: LiveData<List<ViewItems>> get() = _products

    private val _cartItemsCount = MutableLiveData<Int>(0)
    val cartItemsCount: LiveData<String>
        get() =
            _cartItemsCount.map { count -> if (count > 99) CART_ITEMS_COUNT_OVER_100 else count.toString() }

    private val _recentlyViewedProducts = MutableLiveData<List<Product>>(emptyList())
    val recentlyViewedProducts: LiveData<List<Product>> get() = _recentlyViewedProducts

    private val _onNavigateToCartEvent = MutableLiveData<Unit>()
    val onNavigateToCartEvent: LiveData<Unit> get() = _onNavigateToCartEvent

    private val _addPosition = MutableLiveData<Int>()
    val addPosition: LiveData<Int> get() = _addPosition

    private var totalProductsCount: Int = 0
    private var currentIndex = 0
    private var isShowMore = false

    init {
        fetchInitData()
    }

    fun onNavigateToCartClicked() {
        _onNavigateToCartEvent.value = Unit
    }

    fun fetchInitData() {
        fetchCartItemsCount()
        fetchRecentlyViewedData()
        totalProductsCount = productRepository.getProductsSize()
    }

    fun fetchData() {
        val newProducts = productRepository.getProducts(currentIndex, LIMIT_COUNT + 1)
        isShowMore = newProducts.size == LIMIT_COUNT + 1
        val productToUpdate: List<Product> = newProducts.take(LIMIT_COUNT)
        val updatedViewItems: List<ViewItems> = getUpdatedViewItems(productToUpdate)
        _products.postValue(updatedViewItems)
        currentIndex += LIMIT_COUNT
    }

    fun fetchRecentlyViewedData() {
        recentlyViewedRepository.getRecentlyViewed {
            _recentlyViewedProducts.postValue(it)
        }
    }

    private fun getUpdatedViewItems(productToUpdate: List<Product>): List<ViewItems> {
        val currentProducts = products.value ?: emptyList()
        val productsWithoutLoadItem =
            if (currentProducts.lastOrNull() is ShowMore) {
                currentProducts.dropLast(1)
            } else {
                currentProducts
            }

        return buildList {
            _recentlyViewedProducts.value?.takeIf { it.isNotEmpty() }?.let {
                add(RecentlyViewedProducts(it))
                add(Divider)
            }
            addAll(productsWithoutLoadItem)
            addAll(productToUpdate.map(::Products))
            if (isShowMore) add(ShowMore)
        }
    }

    fun fetchCartItemsCount() {
        cartRepository.getAllProductsSize {
            _cartItemsCount.postValue(it)
        }
    }

    fun addCart(
        product: Product,
        position: Int,
    ): Boolean {
        cartRepository.addProduct(product, 1)
        _addPosition.postValue(position)
        fetchCartItemsCount()
        return true
    }

    companion object {
        private const val LIMIT_COUNT: Int = 20
        private const val CART_ITEMS_COUNT_OVER_100: String = "99+"

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val productRepository =
                        (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as ShoppingApplication).productRepository
                    val cartRepository =
                        (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as ShoppingApplication).cartRepository
                    val recentlyViewedRepository =
                        (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as ShoppingApplication)
                            .recentlyViewedRepository
                    ProductViewModel(
                        productRepository = productRepository,
                        cartRepository = cartRepository,
                        recentlyViewedRepository = recentlyViewedRepository,
                    )
                }
            }
    }
}
