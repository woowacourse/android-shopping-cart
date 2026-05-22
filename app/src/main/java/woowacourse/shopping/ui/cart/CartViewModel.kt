package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import java.util.UUID
import kotlin.math.min

class CartViewModel(
    private val cartRepository: CartRepository,
    initialPage: Int = 0,
): ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState(cartRepository.cart, initialPage))

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            cartRepository.cartFlow.collect { newCart ->
                _uiState.update { it.copy(cart = newCart) }
                val maxValidPage =
                    if (newCart.getUniqueItemCount() == 0) 0 else (newCart.getUniqueItemCount() - 1) / ONE_PAGE_ITEM_COUNT
                if (_uiState.value.currentPage > maxValidPage)
                    _uiState.update { it.copy(currentPage = maxValidPage) }
            }
        }
    }

    fun onPrevious() {
        if (_uiState.value.hasPreviousPage)
            _uiState.update { it.copy(currentPage = it.currentPage - 1) }
    }

    fun onNext() {
        if (_uiState.value.hasNextPage)
            _uiState.update { it.copy(currentPage = it.currentPage + 1) }
    }

    fun onIncreaseProduct(id: UUID) {
        val cartProduct = _uiState.value.cart.cartProducts.findSameProduct(id) ?: return
        viewModelScope.launch {
            cartRepository.addProduct(cartProduct.product, 1)
        }
    }

    fun onDecreaseProduct(id: UUID) {
        val cartProduct = _uiState.value.cart.cartProducts.findSameProduct(id) ?: return
        viewModelScope.launch {
            if(cartProduct.amount > 1) {
                cartRepository.decreaseProduct(id, 1)
            } else {
                cartRepository.removeProduct(id)
            }
        }
    }

    fun onDeleteProduct(id: UUID) {
        viewModelScope.launch {
            cartRepository.removeProduct(id)
        }
    }

    fun getPartedItem(
        page: Int,
        pageSize: Int = ONE_PAGE_ITEM_COUNT,
    ): List<CartProduct> {
        require(page >= 0) { "페이지는 0이상이여야 합니다" }
        require(pageSize > 0) { "페이지 사이즈는 0보다 커야 합니다" }

        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, _uiState.value.cart.getUniqueItemCount())
        if(fromIndex >= toIndex || _uiState.value.isCartEmpty) return emptyList()
        return _uiState.value.cart.cartProducts.items.subList(fromIndex, toIndex)
    }

    companion object {
        const val ONE_PAGE_ITEM_COUNT = 5

        fun provideFactory(
            cartRepository: CartRepository,
            restoredPage: Int = 0,
        ) : ViewModelProvider.Factory =
            object: ViewModelProvider.Factory {
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    return CartViewModel(
                        cartRepository,
                        restoredPage,
                    ) as T
                }
            }
    }
}
