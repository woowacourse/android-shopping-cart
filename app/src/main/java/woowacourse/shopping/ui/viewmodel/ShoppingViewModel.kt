package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.PurchaseProductsRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.PurchaseProduct

class ShoppingViewModel(
    private val purchaseProductsRepository: PurchaseProductsRepository
): ViewModel() {
    val cart: StateFlow<Cart> = purchaseProductsRepository.getCart()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Cart()
        )

    val totalCount: StateFlow<Int> = cart
        .map { it.totalCountOfPurchaseProducts() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun isContained(id: String): Boolean =
        purchaseProductsRepository.isContained(id)

    fun getTotalPriceOfSpecificProduct(id: String): Int =
        cart.value.totalPriceOfSpecificPurchaseProduct(id)

    fun getTotalCountOfSpecificProduct(id: String): Int =
        cart.value.totalCountOfSpecificPurchaseProduct(id)

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
}

class ShoppingViewModelFactory(
    private val purchaseProductsRepository: PurchaseProductsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(purchaseProductsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
