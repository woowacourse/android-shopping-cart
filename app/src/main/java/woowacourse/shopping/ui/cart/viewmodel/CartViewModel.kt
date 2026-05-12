package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.local.AppDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.ui.cart.state.CartUiState
import woowacourse.shopping.ui.model.DetailProductUiModel

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    private var cart = Cart()

    init {
        observeCart()
    }

    private fun observeCart() {
        cartRepository.getCart()
            .onEach { cartItems ->
                cart = Cart(cartItems)

                val totalPage = calTotalPage()
                if (_uiState.value.page > totalPage) {
                    _uiState.update { it.copy(page = maxOf(1, totalPage)) }
                }
                syncUiState()
            }.launchIn(viewModelScope)
    }

    fun incrementQuantity(productId: String) {
        val product = cart.findProductById(productId) ?: return
        val updatedItem = cart.plusProduct(product, Quantity(1)).findCartItemById(productId) ?: return

        viewModelScope.launch {
            cartRepository.updateCartItem(updatedItem)
        }
    }

    fun decrementQuantity(productId: String) {
        val product = cart.findProductById(productId) ?: return
        val updatedItem = cart.minusProduct(product, Quantity(1)).findCartItemById(productId)

        viewModelScope.launch {
            if (updatedItem == null) {
                cartRepository.deleteCartItem(productId)
            } else {
                cartRepository.updateCartItem(updatedItem)
            }
        }
    }

    fun deleteCartItem(productId: String) {
        viewModelScope.launch {
            cartRepository.deleteCartItem(productId)
        }
    }

    fun onLeftClick() {
        if (_uiState.value.page == 1) return
        _uiState.update { it.copy(page = it.page - 1) }
        syncUiState()
    }

    fun onRightClick() {
        if (_uiState.value.page == calTotalPage()) return
        _uiState.update { it.copy(page = it.page + 1) }
        syncUiState()
    }

    private fun syncUiState() {
        _uiState.update { state ->
            state.copy(
                cartItems = getPaginationItems().map { item ->
                    item.toUiModel()
                },
                totalPage = calTotalPage(),
            )
        }
    }

    private fun getPaginationItems(): List<CartItem> {
        if (cart.cartItems.isEmpty()) return emptyList()

        val fromIndex = (_uiState.value.page - 1) * PAGE_SIZE

        if (fromIndex >= cart.cartItems.size) return emptyList()

        val toIndex = minOf(fromIndex + PAGE_SIZE, cart.cartItems.size)
        return cart.cartItems.subList(fromIndex, toIndex)
    }

    private fun calTotalPage(): Int {
        if (cart.cartItems.isEmpty()) return 1
        return (cart.cartItems.size + PAGE_SIZE - 1) / PAGE_SIZE
    }

    private fun CartItem.toUiModel(): DetailProductUiModel = DetailProductUiModel.of(
        name = product.name,
        price = totalPrice.amount,
        imageUrl = product.imageUrl,
        id = product.id,
        quantity = quantity.count,
    )

    companion object {
        private const val PAGE_SIZE = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                val database = AppDatabase.getDatabase(context)

                val cartRepository = CartRepositoryImpl(database.cartDao())
                CartViewModel(cartRepository)
            }
        }
    }
}
