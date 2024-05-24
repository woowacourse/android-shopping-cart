package woowacourse.shopping.productList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.DummyProductStore
import woowacourse.shopping.repository.RecentlyViewedRepository

class ProductListViewModel(application: Application) : AndroidViewModel(application) {
    private val dummyProductStore by lazy { DummyProductStore() }
    private var currentIndex = 1

    private val recentlyViewedRepository = RecentlyViewedRepository(application)

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _loadedProducts: MutableLiveData<List<Product>> =
        MutableLiveData(loadProducts())
    val loadedProducts: LiveData<List<Product>>
        get() = _loadedProducts

    private val _recentlyViewedProducts = MutableLiveData<List<Product>>()
    val recentlyViewedProducts: LiveData<List<Product>> get() = _recentlyViewedProducts

    init {
        ShoppingCart.initialize(application)
        loadCartItems()
        loadRecentlyViewedProducts()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            val cartItemsFromDb = ShoppingCart.getCartItems()
            _cartItems.value = cartItemsFromDb
        }
    }

    private fun loadRecentlyViewedProducts() {
        viewModelScope.launch {
            val recentlyViewedEntities = recentlyViewedRepository.getRecentProducts()
            val cartItemIds = _cartItems.value?.map { it.productId } ?: emptyList()
            val recentlyViewedProducts = recentlyViewedEntities
                .filter { it.productId !in cartItemIds }
                .map { dummyProductStore.findById(it.productId) }
            _recentlyViewedProducts.value = recentlyViewedProducts
        }
    }

    fun addProductToRecentlyViewed(productId: Int) {
        viewModelScope.launch {
            recentlyViewedRepository.addProduct(productId)
            loadRecentlyViewedProducts()
        }
    }

    fun loadMoreProducts() {
        val newProducts = loadProducts()
        val currentProducts = _loadedProducts.value.orEmpty()
        _loadedProducts.value = currentProducts + newProducts
    }

    fun increaseQuantity(productId: Int) {
        viewModelScope.launch {
            ShoppingCart.addProductToCart(productId)
            loadCartItems()
            loadRecentlyViewedProducts()
        }
    }

    fun decreaseQuantity(productId: Int) {
        viewModelScope.launch {
            ShoppingCart.subtractProductCount(productId)
            loadCartItems()
            loadRecentlyViewedProducts()
        }
    }

    private fun loadProducts(): List<Product> {
        val result = dummyProductStore.loadDataAsNeeded(currentIndex)
        currentIndex += COUNT_EACH_LOADING
        return result
    }

    companion object {
        private const val COUNT_EACH_LOADING = 20
    }
}
