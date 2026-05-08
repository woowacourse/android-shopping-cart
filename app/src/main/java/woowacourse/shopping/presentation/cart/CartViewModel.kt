package woowacourse.shopping.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    val totalItemCount: Int
        get() = cartRepository.getTotalItemCount()

    val lastPageIndex: Int
        get() = if (totalItemCount == 0) 0 else (totalItemCount - 1) / DEFAULT_PAGE_SIZE

    val hasPreviousPage: Boolean
        get() = _uiState.value.currentPageIndex > 0

    val hasNextPage: Boolean
        get() = _uiState.value.currentPageIndex < lastPageIndex

    val hasMoreItems: Boolean
        get() = totalItemCount > DEFAULT_PAGE_SIZE

    init {
        refresh()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProduct(productId: Uuid) {
        cartRepository.deleteProduct(productId)
        refresh()
    }

    fun increaseQuantity(product: Product) {
        cartRepository.increaseQuantity(product, 1)
        refresh()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun decreaseQuantity(productId: Uuid) {
        cartRepository.decreaseQuantity(productId)
        refresh()
    }

    fun goToPreviousPage() {
        if (!hasPreviousPage) return

        _uiState.update {
            it.copy(
                currentPageIndex = it.currentPageIndex - 1,
            )
        }

        refreshPagedCart()
    }

    fun goToNextPage() {
        if (!hasNextPage) return

        _uiState.update {
            it.copy(
                currentPageIndex = it.currentPageIndex + 1,
            )
        }

        refreshPagedCart()
    }

    private fun refresh() {
        adjustCurrentPage()
        refreshPagedCart()
    }

    private fun refreshPagedCart() {
        _uiState.update {
            it.copy(
                cart =
                    cartRepository.getPagingItems(
                        page = it.currentPageIndex,
                        pageSize = DEFAULT_PAGE_SIZE,
                    ),
            )
        }
    }

    private fun adjustCurrentPage() {
        if (_uiState.value.currentPageIndex > lastPageIndex) {
            _uiState.update {
                it.copy(currentPageIndex = lastPageIndex)
            }
        }
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
