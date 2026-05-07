package woowacourse.shopping.ui.screens.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.compareTo

class CartViewModel(
    private val cartRepository: CartRepository = CartRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() =
        launchWithLoading {
            refreshPage(_uiState.value.curPage)
        }

    fun deleteCartItem(id: String) =
        launchWithLoading {
            cartRepository.deleteItem(id)

            val curPage = _uiState.value.curPage

            if (curPage > 1 && cartRepository.isLastPage(curPage - 1)) {
                refreshPage(curPage - 1)
            } else {
                refreshPage(curPage)
            }
        }

    fun getPrevPage() =
        launchWithLoading {
            val curPage = _uiState.value.curPage
            if (curPage == 1) return@launchWithLoading

            refreshPage(curPage - 1)
        }

    fun getNextPage() =
        launchWithLoading {
            if (_uiState.value.isLast) return@launchWithLoading

            val nextPage = _uiState.value.curPage + 1
            refreshPage(nextPage)
        }

    private suspend fun refreshPage(page: Int) {
        val items = cartRepository.getCartItemByPage(page)
        val isLast = cartRepository.isLastPage(page)

        _uiState.update { it.copy(curPage = page, cartItems = items, isLast = isLast) }
    }

    private fun launchWithLoading(action: suspend () -> Unit) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                action()
            } catch (e: Exception) {
                Log.e("CartViewModel", e.message.toString())
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
