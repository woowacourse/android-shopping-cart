package woowacourse.shopping.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.data.local.repository.RecentlyViewedProductRepository
import woowacourse.shopping.data.remote.repository.WebServerRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.PurchaseProduct
import kotlin.time.Duration.Companion.seconds

class ShoppingViewModel(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val webServerRepository: WebServerRepository
): ViewModel() {
    val cart: StateFlow<Cart> = purchaseProductsRepository.getCart()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Cart()
        )

    private val _products = MutableStateFlow<Products>(Products())
    val products: StateFlow<Products> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchProducts()
    }

    fun fetchProducts(page: Int = 0){
        viewModelScope.launch {
            _isLoading.value = true
            delay(2.seconds)
            try {
                val response = webServerRepository.getProducts(page, PAGE_SIZE)
                _products.value += Products(response)
            } catch (e: Exception){
                Log.e("Web Server Error", "${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPurchaseProduct(purchaseProduct: PurchaseProduct) {
        viewModelScope.launch {
            purchaseProductsRepository.insert(purchaseProduct)
        }
    }

    fun updateCountWithID(id: String, updateAmount: Int) {
        viewModelScope.launch {
            purchaseProductsRepository.updateCount(id, updateAmount)
        }
    }

    fun removeWithID(id: String) {
        viewModelScope.launch {
            purchaseProductsRepository.deletePurchaseProduct(id)
        }
    }

    val viewingHistory: StateFlow<Products> = recentlyViewedProductRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Products()
        )

    val lastViewedProduct: StateFlow<Product?> = recentlyViewedProductRepository.getLatestItem()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateHistory(product: Product) {
        viewModelScope.launch {
            recentlyViewedProductRepository.updateList(product)
        }
    }

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    fun loadMore() {
        _currentIndex.value++
        fetchProducts(currentIndex.value)
    }

    companion object{
        private val PAGE_SIZE = 20
    }
}

class ShoppingViewModelFactory(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val webServerRepository: WebServerRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(purchaseProductsRepository, recentlyViewedProductRepository, webServerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
