package woowacourse.shopping.features.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.cart.repository.CartRepository
import kotlin.math.ceil

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    private var cart = Cart(CartItems(emptyList<CartItem>()))
    private var pageCartItems = emptyList<CartItemUiModel>()
    private var isFirstPage = true
    private var isLastPage = true
    private var currentPage = 0
    private var totalPages = 0

    init {
        loadCartPage()
    }

    fun isMinusEnabled(cartItemUiModel: CartItemUiModel): Boolean = cartItemUiModel.quantity > 1

    fun loadCartPage() {
        viewModelScope.launch {
            if (!Cart.isPageValid(currentPage)) currentPage = 0
            cart = cartRepository.getCart()
            totalPages = ceil(cartRepository.getTotalCartCount().toDouble() / PAGE_SIZE).toInt()
            if (currentPage >= totalPages && currentPage != 0) {
                currentPage = totalPages - 1
            }
            pageCartItems = cart.getPage(currentPage, PAGE_SIZE).map { cartItem ->
                cartItem.toCartUiModel()
            }
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
    }

    fun removeCartItem(cartItemUiModel: CartItemUiModel) {
        viewModelScope.launch {
            cartRepository.removeCartItem(cartItemUiModel.toCartItem())
            loadCartPage()
        }
    }

    fun increaseCartItem(cartItemUiModel: CartItemUiModel) {
        viewModelScope.launch {
            cartRepository.addCartItem(cartItemUiModel.toCartItem(), 1)
            loadCartPage()
        }
    }

    fun decreaseCartItem(cartItemUiModel: CartItemUiModel) {
        viewModelScope.launch {
            cartRepository.minusCartItem(cartItemUiModel.toCartItem(), 1)
            loadCartPage()
        }
    }

    fun goToNextPage() {
        if (isLastPage) return
        currentPage += 1

        _uiState.update {
            it.copy(
                currentPage = currentPage,
            )
        }
        loadCartPage()
    }

    fun goToPreviousPage() {
        if (isFirstPage) return
        currentPage -= 1

        _uiState.update {
            it.copy(
                currentPage = currentPage,
            )
        }
        loadCartPage()
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}

class CartViewModelFactory(
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
