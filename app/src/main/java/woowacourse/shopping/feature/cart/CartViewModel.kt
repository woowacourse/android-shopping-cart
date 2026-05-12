package woowacourse.shopping.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.ProductNotFoundException
import woowacourse.shopping.feature.common.state.ProductUiModel

sealed interface CartEvent {
    data class FatalError(
        val message: String,
    ) : CartEvent
}

data class CartUiState(
    val isLoading: Boolean = true,
    val page: Int = 1,
    val paginatedCartContents: List<ProductUiModel> = emptyList(),
)

class CartViewModel(
    private val initialPageSize: Int = 5,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _event = Channel<CartEvent>(Channel.BUFFERED)
    val event: Flow<CartEvent> = _event.receiveAsFlow()

    private var cart: Cart = Cart(emptyList())

    fun initialLoading() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            cart = getCart()
            val cartContents = pagination(
                page = 1,
            )
            _uiState.update { it.copy(isLoading = false, paginatedCartContents = cartContents) }
        }
    }

    private suspend fun getCart(): Cart {
        val cart = cartRepository.loadCart()
        return cart
    }

    private suspend fun getCartSize(): Int {
        val cartSize = cartRepository.loadCartSize()
        return cartSize
    }

    fun isStartPage(): Boolean = uiState.value.page == 1

    fun isEndPage(): Boolean = uiState.value.page >= lastPage(initialPageSize)

    private fun lastPage(pageSize: Int): Int {
        val size = cart.cartContentsSizeOf()
        if (size == 0) return 1
        return (size + pageSize - 1) / pageSize
    }

    fun moveToPreviousPage() {
        val page = uiState.value.page - 1
        _uiState.update {
            it.copy(
                page = page,
            )
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val cartContents = pagination(page)
            _uiState.update { it.copy(isLoading = false, paginatedCartContents = cartContents) }
        }
    }

    fun moveToNextPage() {
        val page = uiState.value.page + 1
        _uiState.update {
            it.copy(
                page = page,
            )
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val cartContents = pagination(page)
            _uiState.update { it.copy(isLoading = false, paginatedCartContents = cartContents) }
        }
    }

    private suspend fun pagination(
        page: Int,
        pageSize: Int = 5,
    ): List<ProductUiModel> {
        val toIndex = minOf(page * pageSize, cart.cartContentsSizeOf())
        val startIndex = (page - 1) * pageSize

        val cartContents = cartRepository
            .pagination(startIndex, toIndex)
            .map(::toProductUiModel)
        return cartContents
    }

    fun increase(productId: String) = guardFatal {
        val product = cart.getProductList().firstOrNull { it.id == productId }
            ?: throw ProductNotFoundException(productId)

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            cartRepository.increase(product)
            cart = getCart()
            val cartContents = pagination(uiState.value.page)
            _uiState.update { it.copy(isLoading = false, paginatedCartContents = cartContents) }
        }
    }

    fun decrease(productId: String) = guardFatal {
        cart.getProductList().firstOrNull { it.id == productId }
            ?: throw ProductNotFoundException(productId)

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            cartRepository.decrease(productId)
            cart = getCart()
            val cartContents = pagination(uiState.value.page)
            _uiState.update { it.copy(isLoading = false, paginatedCartContents = cartContents) }
        }
    }

    private inline fun guardFatal(block: () -> Unit) {
        try {
            block()
        } catch (e: ProductNotFoundException) {
            _event.trySend(CartEvent.FatalError(e.message ?: "알 수 없는 오류가 발생했습니다."))
        }
    }

    fun deleteCartItem(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            cartRepository.remove(id)
            val cartContents = pagination(uiState.value.page)
            _uiState.update { it.copy(isLoading = false, paginatedCartContents = cartContents) }
        }
    }

    fun toProductUiModel(cartContent: CartContent): ProductUiModel {
        val product = cartContent.product
        return ProductUiModel(
            name = product.name,
            price = product.priceAmount(),
            imageUrl = product.imageUrl,
            id = product.id,
            quantity = cartContent.quantity,
        )
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ShoppingApplication
                CartViewModel(5, app.cartRepository)
            }
        }
    }
}
