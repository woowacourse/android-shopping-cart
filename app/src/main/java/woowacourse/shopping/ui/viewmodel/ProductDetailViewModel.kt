package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ProductDetailActivity
import woowacourse.shopping.data.repository.PurchaseProductsRepository
import woowacourse.shopping.domain.PurchaseProduct

class ProductDetailViewModel(
    private val purchaseProductsRepository: PurchaseProductsRepository
): ViewModel() {
    private val _count = MutableStateFlow(1)

    val countState = _count.asStateFlow()

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
}

class ProductDetailViewModelFactory(
    private val purchaseProductsRepository: PurchaseProductsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(purchaseProductsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}