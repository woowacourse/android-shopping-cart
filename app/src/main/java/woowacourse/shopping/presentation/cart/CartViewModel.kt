package woowacourse.shopping.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.repository.CartRepository

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<CartUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    val lastPageIndex: Int
        get() = getLastPageIndex(_uiState.value.totalItemCount)

    val hasPreviousPage: Boolean
        get() = _uiState.value.currentPageIndex > 0

    val hasNextPage: Boolean
        get() = _uiState.value.currentPageIndex < lastPageIndex

    val hasMoreItems: Boolean
        get() = _uiState.value.totalItemCount > DEFAULT_PAGE_SIZE

    init {
        refresh()
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            cartRepository.deleteProduct(productId)
            refresh()
            _uiEvent.send(CartUiEvent.ShowMessage("삭제되었습니다."))
            dismissDeleteDialog()
        }
    }

    fun increaseQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.increaseQuantity(productId, 1)
            refresh()
        }
    }

    fun decreaseQuantity(productId: Int) {
        val item =
            _uiState.value.cart.cartItems
                .find { it.product.productId == productId }

        if (item?.quantity == 1) {
            _uiState.update { it.copy(deleteProductId = productId) }
        } else {
            viewModelScope.launch {
                cartRepository.decreaseQuantity(productId)
                refresh()
            }
        }
    }

    fun goToPreviousPage() {
        if (!hasPreviousPage) return

        _uiState.update {
            it.copy(
                currentPageIndex = it.currentPageIndex - 1,
            )
        }

        refresh()
    }

    fun goToNextPage() {
        if (!hasNextPage) return

        _uiState.update {
            it.copy(
                currentPageIndex = it.currentPageIndex + 1,
            )
        }

        refresh()
    }

    fun showDeleteDialog(productId: Int) {
        _uiState.update { it.copy(deleteProductId = productId) }
    }

    fun dismissDeleteDialog() {
        _uiState.update { it.copy(deleteProductId = null) }
    }

    private fun refresh() {
        viewModelScope.launch {
            val totalItemCount = cartRepository.getTotalItemCount()
            val lastPageIndex = getLastPageIndex(totalItemCount)

            val adjustedPageIndex =
                _uiState.value.currentPageIndex.coerceAtMost(lastPageIndex)

            val cart =
                cartRepository.getPagingItems(
                    page = adjustedPageIndex,
                    pageSize = DEFAULT_PAGE_SIZE,
                )

            _uiState.update {
                it.copy(
                    cart = cart,
                    totalItemCount = totalItemCount,
                    currentPageIndex = adjustedPageIndex,
                )
            }
        }
    }

    private fun getLastPageIndex(totalItemCount: Int): Int =
        if (totalItemCount == 0) {
            0
        } else {
            (totalItemCount - 1) / DEFAULT_PAGE_SIZE
        }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 5
    }
}

class CartViewModelFactory(
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(
                cartRepository = cartRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
