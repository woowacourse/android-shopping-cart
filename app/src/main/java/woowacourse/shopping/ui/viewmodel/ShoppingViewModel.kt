package woowacourse.shopping.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.local.entity.PurchaseProductEntity
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.data.local.repository.RecentlyViewedProductRepository
import woowacourse.shopping.data.remote.repository.ProductRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.domain.PurchaseProducts

class ShoppingViewModel(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    val cartEntities: StateFlow<List<PurchaseProductEntity>?> =
        purchaseProductsRepository
            .getAll()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

    val recentlyViewedEntities: StateFlow<List<RecentlyViewedProductEntity>?> =
        recentlyViewedProductRepository
            .getAll()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    private val _products = MutableStateFlow<Products>(Products())
    val products: StateFlow<Products> = _products.asStateFlow()

    val cart: StateFlow<Cart> =
        combine(cartEntities, products) { entities, allProducts ->
            val purchaseProducts =
                entities?.mapNotNull { entity ->
                    val product = allProducts.findWithId(entity.id)
                    product?.let { PurchaseProduct(it, entity.count) }
                } ?: emptyList()
            Cart(PurchaseProducts(purchaseProducts))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Cart(),
        )

    val recentlyViewedProducts: StateFlow<Products> =
        combine(recentlyViewedEntities, products) { entities, allProducts ->
            val productList =
                entities?.mapNotNull { entity ->
                    allProducts.findWithId(entity.id)
                } ?: emptyList()
            Products(productList)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Products()
        )

    val lastViewProductId: StateFlow<String?> = recentlyViewedProductRepository.getLatestItem()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    val totalCartCount: StateFlow<Int> = cartEntities
        .map { entities -> entities?.sumOf { it.count } ?: 0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchProducts()
    }

    fun fetchProducts(page: Int = 0) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = productRepository.getProducts(page, PAGE_SIZE)
                _products.update { it + Products(response) }
            } catch (e: Exception) {
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

    fun updateCountWithID(
        id: String,
        updateAmount: Int,
    ) {
        viewModelScope.launch {
            purchaseProductsRepository.updateCount(id, updateAmount)
        }
    }

    fun removeWithID(id: String) {
        viewModelScope.launch {
            purchaseProductsRepository.deletePurchaseProduct(id)
        }
    }

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

    companion object {
        private val PAGE_SIZE = 20
    }
}

class ShoppingViewModelFactory(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(purchaseProductsRepository, recentlyViewedProductRepository, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
