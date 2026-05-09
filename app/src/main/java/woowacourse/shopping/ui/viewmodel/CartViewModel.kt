package woowacourse.shopping.ui.viewmodel

import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.compose.ui.text.style.TextDecoration.Companion.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.PurchaseProductsRepository
import woowacourse.shopping.domain.Cart

class CartViewModel(
    private val purchaseProductsRepository: PurchaseProductsRepository
): ViewModel() {
    private val _currentPage: MutableStateFlow<Int> = MutableStateFlow(0)

    val currentPage :StateFlow<Int> = _currentPage.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedCart: StateFlow<Cart> = _currentPage.flatMapLatest { page ->
        purchaseProductsRepository.partedProducts(page, PAGE_SIZE)
    }.onEach {
        println("페이지 변경됨")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Cart()
    )

    val productCount: StateFlow<Int> = purchaseProductsRepository.getProductCount().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun next() {
        _currentPage.update {
            if (productCount.value > (it + 1) * PAGE_SIZE) it + 1 else it
        }
    }

    fun prev() { _currentPage.update { if(it > 0) it - 1 else 0 } }

    val nextEnable: StateFlow<Boolean> = combine(currentPage, productCount) { page, count ->
        page < (count - 1) / PAGE_SIZE
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val prevEnable: StateFlow<Boolean> = currentPage
        .map { it > 0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val isPageable: StateFlow<Boolean> = productCount
        .map { it > PAGE_SIZE }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

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

    companion object {
        private val PAGE_SIZE = 5
    }
}

class CartViewModelFactory(
    private val purchaseProductsRepository: PurchaseProductsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(purchaseProductsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
