package woowacourse.shopping.features.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.repository.CartRepository
import kotlin.math.ceil

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    var pageCartItems = emptyList<CartItem>()
    var isFirstPage = true
    var isLastPage = true
    var currentPage = 0
    var totalPages = 0

    init {
        loadCartPage()
    }

    fun isMinusEnabled(cartItem: CartItem): Boolean = cartItem.quantity.value > 1

    fun loadCartPage() {
        if (!Cart.isPageValid(currentPage)) currentPage = 0
        totalPages = ceil(cartRepository.getTotalCartCount().toDouble() / PAGE_SIZE).toInt()
        if (currentPage >= totalPages && currentPage != 0) {
            currentPage = totalPages - 1
        }
        val cart = cartRepository.getCart()
        pageCartItems = cart.getPage(currentPage, PAGE_SIZE)
        isFirstPage = currentPage == 0
        isLastPage = currentPage == totalPages - 1 || totalPages == 0
        _uiState.update {
            it.copy(
                pageCartItems = pageCartItems,
                totalPageCount = totalPages,
                currentPage = currentPage,
                isFirstPage = isFirstPage,
                isLastPage = isLastPage,
            )
        }
    }

    fun removeCartItem(cartItem: CartItem) {
        cartRepository.removeCartItem(cartItem)
        loadCartPage()
    }

    fun increaseCartItem(cartItem: CartItem) {
        cartRepository.addCartItem(cartItem, 1)
        loadCartPage()
    }

    fun decreaseCartItem(cartItem: CartItem) {
        cartRepository.minusCartItem(cartItem, 1)
        loadCartPage()
    }

    fun goToNextPage() {
        if (isLastPage) return
        currentPage += 1
        _uiState.update {
            it.copy(
                currentPage = currentPage
            )
        }
        loadCartPage()
    }

    fun goToPreviousPage() {
        if (isFirstPage) return
        currentPage -= 1
        _uiState.update {
            it.copy(
                currentPage = currentPage
            )
        }
        loadCartPage()
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}

class CartViewModelFactory(
    private val cartRepository: CartRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
