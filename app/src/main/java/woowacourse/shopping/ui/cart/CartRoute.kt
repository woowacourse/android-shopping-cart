package woowacourse.shopping.ui.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel

@Composable
fun CartRoute(
    cartViewModel: CartViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
) {
    val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()

    CartScreen(
        cartItems = uiState.cartItems,
        onIncrement = { id ->
            cartViewModel.incrementQuantity(id)
        },
        onDecrement = { id ->
            cartViewModel.decrementQuantity(id)
        },
        onCloseClick = onNavigateToHome,
        onDelete = { id ->
            cartViewModel.deleteCartItem(id)
        },
        page = uiState.page,
        onLeftClick = { cartViewModel.onLeftClick() },
        onRightClick = { cartViewModel.onRightClick() },
        isLeftEnable = uiState.isStartPage,
        isRightEnable = uiState.isEndPage,
    )
}
