package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.data.local.repository.RecentlyViewedProductRepository
import woowacourse.shopping.data.remote.repository.ProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct

class ProductDetailViewModel(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
    private val selectedProductId: String,
    private val lastViewedProductId: String?,
) : ViewModel() {
    private val _count = MutableStateFlow(1)
    val countState = _count.asStateFlow()

    val selectedProduct: StateFlow<Product?> = flow {
        emit(productRepository.getProduct(selectedProductId))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val lastViewedProduct: StateFlow<Product?> = flow {
        lastViewedProductId?.let {
            emit(productRepository.getProduct(lastViewedProductId))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun addCount() {
        _count.update { it + 1 }
    }

    fun minusCount() {
        if (countState.value > 1) {
            _count.update { it - 1 }
        }
    }

    fun addPurchaseProduct(purchaseProduct: PurchaseProduct) {
        viewModelScope.launch { purchaseProductsRepository.insert(purchaseProduct) }
    }

    fun updateHistory(product: Product) {
        viewModelScope.launch {
            recentlyViewedProductRepository.updateList(product)
        }
    }
}

class ProductDetailViewModelFactory(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
    private val selectedProductId: String,
    private val lastViewedProductId: String?,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(
                purchaseProductsRepository,
                recentlyViewedProductRepository,
                productRepository,
                selectedProductId,
                lastViewedProductId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
