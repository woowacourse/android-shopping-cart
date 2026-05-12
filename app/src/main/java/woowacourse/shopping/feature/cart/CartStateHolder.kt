package woowacourse.shopping.feature.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.feature.cart.model.toUiModel

class CartStateHolder(
    private val cartRepository: CartRepository,
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val pageSize = 5
    private var currentPage = 0
    private var totalSize = 0

    private val totalPages get() = (totalSize + pageSize - 1) / pageSize

    var uiState: CartUiState by mutableStateOf(CartUiState())
        private set

    init {
        scope.launch(Dispatchers.IO) {
            totalSize = cartRepository.getCartItemCount()
            loadPage(0)
        }
        cartRepository.getCartItems()
            .onEach { loadPage(currentPage) }
            .launchIn(scope)
    }

    fun nextPage() {
        if (!uiState.isLastPage && !uiState.isLoading) loadPage(currentPage + 1)
    }

    fun prevPage() {
        if (!uiState.isFirstPage && !uiState.isLoading) loadPage(currentPage - 1)
    }

    fun removeFromCart(productId: String) {
        scope.launch(Dispatchers.IO) {
            cartRepository.deleteCartItem(productId)
            totalSize = cartRepository.getCartItemCount()
        }
    }

    fun increaseQuantity(productId: String) {
        scope.launch(Dispatchers.IO) {
            cartRepository.increaseCartItemQuantity(productId)
        }
    }

    fun decreaseQuantity(productId: String) {
        scope.launch(Dispatchers.IO) {
            cartRepository.decreaseCartItemQuantity(productId)
        }
    }

    private fun loadPage(page: Int) {
        currentPage = page
        scope.launch {
            uiState = uiState.copy(isLoading = true)
            val cartItems = withContext(Dispatchers.IO) {
                cartRepository.getPagingCartItems(page, pageSize).toUiModel()
            }
            uiState =
                CartUiState(
                    cartItems = cartItems,
                    displayPageNumber = page + 1,
                    showControls = totalPages > 1,
                    isFirstPage = page == 0,
                    isLastPage = totalPages == 0 || page == totalPages - 1,
                    isLoading = false,
                )
        }
    }
}

@Composable
fun retainCartStateHolder(): CartStateHolder {
    val context = androidx.compose.ui.platform.LocalContext.current
    return retain {
        val application = context.applicationContext as ShoppingApplication
        CartStateHolder(CartRepositoryImpl(application.database.cartDao()))
    }
}
