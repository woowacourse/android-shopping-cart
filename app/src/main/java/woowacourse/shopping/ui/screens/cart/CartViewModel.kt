package woowacourse.shopping.ui.screens.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ui.screens.util.toUiModel

class CartViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
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

    fun plusAmount(id: String) =
        launchWithLoading {
            cartRepository.addItem(id, 1)
            refreshPage(_uiState.value.curPage)
        }

    fun minusAmount(id: String) =
        launchWithLoading {
            cartRepository.minusItemAmount(id)
            refreshPage(_uiState.value.curPage)
        }

    fun deleteCartItem(id: String) =
        launchWithLoading {
            val isLastPage = _uiState.value.isLast
            val curItemSize = _uiState.value.items.size
            cartRepository.deleteItem(id)

            val curPage = _uiState.value.curPage

            if (curPage > 1 && isLastPage && curItemSize == 1) {
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
        val cart = cartRepository.getCartItemByPage(page, PAGE_SIZE)
        val products = productRepository.getProductsByIds(cart.items.map { it.productId })

        val uiModels = cart.items.map { item ->
            item.toUiModel(products.first { it.id == item.productId })
        }
        _uiState.update { it.copy(curPage = page, items = uiModels, isLast = cart.isLast) }
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

    companion object {

        private const val PAGE_SIZE = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ShoppingApplication)
                CartViewModel(
                    productRepository = application.productRepository,
                    cartRepository = application.cartRepository,
                )
            }
        }
    }
}
