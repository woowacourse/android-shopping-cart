package woowacourse.shopping.productList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.db.ShoppingCartDatabase
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductEntity
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.RecentlyViewedRepository
import woowacourse.shopping.service.MockWebService
import kotlin.concurrent.thread

class ProductListViewModel(application: Application) : AndroidViewModel(application) {
    private val dummyProductStore by lazy { MockWebService() }
    private var currentIndex = 1

    private val recentlyViewedRepository = RecentlyViewedRepository(application)

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _loadedProducts: MutableLiveData<List<Product>> =
        MutableLiveData(loadProducts())
    val loadedProducts: LiveData<List<Product>>
        get() = _loadedProducts

    private val _recentlyViewedEntities = recentlyViewedRepository.getRecentProducts()
    private val _recentlyViewedProducts = MediatorLiveData<List<Product>>()
    val recentlyViewedProducts: LiveData<List<Product>> get() = _recentlyViewedProducts

    init {
        ShoppingCartDatabase.initialize(application)
        loadCartItems()

        _recentlyViewedProducts.addSource(_cartItems) {
            _cartItems.value?.map { it.productId }?.let { it1 ->
                _recentlyViewedEntities.value?.let { it2 ->
                    updateRecentlyViewedProducts(
                        it1,
                        it2
                    )
                }
            }
        }
        _recentlyViewedProducts.addSource(_recentlyViewedEntities) {
            _cartItems.value?.map { it.productId }?.let { it1 ->
                updateRecentlyViewedProducts(
                    it1,
                    _recentlyViewedEntities.value.orEmpty()
                )
            }
        }
    }

    private fun updateRecentlyViewedProducts(
        cartItemIds: List<Int>,
        recentlyViewedEntities: List<RecentlyViewedProductEntity>
    ) {
        var recentlyViewedProducts: List<Product> = emptyList()
        thread {
            recentlyViewedProducts =
                recentlyViewedEntities
                    .filter { it.productId !in cartItemIds }
                    .map { dummyProductStore.findProductById(it.productId) }
        }.join()
        _recentlyViewedProducts.value = recentlyViewedProducts
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            val cartItemsFromDb = ShoppingCartDatabase.getCartItems()
            _cartItems.value = cartItemsFromDb
        }
    }

    fun loadMoreProducts() {
        val newProducts = loadProducts()
        val currentProducts = _loadedProducts.value.orEmpty()
        _loadedProducts.value = currentProducts + newProducts
    }

    fun increaseQuantity(productId: Int) {
        viewModelScope.launch {
            ShoppingCartDatabase.addProductToCart(productId)
            loadCartItems()
        }
    }

    fun decreaseQuantity(productId: Int) {
        viewModelScope.launch {
            ShoppingCartDatabase.subtractProductCount(productId)
            loadCartItems()
        }
    }

    private fun loadProducts(): List<Product> {
        var result = emptyList<Product>()
        thread {
            result = MockWebService().getPagingProducts(currentIndex, COUNT_EACH_LOADING)
            currentIndex += COUNT_EACH_LOADING
        }.join()

        return result
    }

    companion object {
        private const val COUNT_EACH_LOADING = 20
    }
}
