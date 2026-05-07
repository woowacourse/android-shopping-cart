package woowacourse.shopping.presentation.cart.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.model.RemoveItemResult
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.cart.model.CartUiState
import woowacourse.shopping.presentation.cart.model.toUiModel
import kotlin.math.min

class CartViewModel(
    private val cartRepository: CartRepository = RepositoryProvider.cartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()
    private val pageSize = 5

    suspend fun loadCartItems() {
        if (uiState.value.isLoading) return
        _uiState.update {
            it.copy(isLoading = true)
        }

        try {
            val cart = cartRepository.getCart()
            val items = cart.items.map { it.toUiModel() }
            val maxPage = if (items.isEmpty()) 0 else (items.size - 1) / pageSize

            _uiState.update {
                val page = it.page.coerceIn(0, maxPage)
                val fromIndex = page * pageSize
                val toIndex = min(fromIndex + pageSize, items.size)
                it.copy(
                    page = page,
                    totalCartSize = items.size,
                    currentCartItems = items.subList(fromIndex, toIndex),
                    isCanMoveNext = toIndex < items.size,
                )
            }
        } finally {
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    suspend fun deleteItem(productId: String): RemoveItemResult {
        val result = cartRepository.deleteItem(productId)
        if (result is RemoveItemResult.Success) loadCartItems()
        return result
    }

    suspend fun nextPage() {
        if (!uiState.value.isCanMoveNext) return
        _uiState.update {
            it.copy(page = it.page + 1)
        }
        loadCartItems()
    }

    suspend fun previousPage() {
        if (uiState.value.page == 0) return
        _uiState.update {
            it.copy(page = it.page - 1)
        }
        loadCartItems()
    }
}
