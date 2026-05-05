package woowacourse.shopping.ui.cart.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.domain.CART_PAGE_SIZE
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.InMemoryCartRepository
import woowacourse.shopping.ui.cart.component.CartBody
import woowacourse.shopping.ui.cart.component.CartTopAppBar
import woowacourse.shopping.ui.cart.state.rememberCartState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    cartProducts: CartRepository,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberCartState()
    val cartItems = cartProducts.getCartProducts()
    val lastPageIndex =
        if (cartItems.isEmpty()) 0 else (cartItems.size - 1) / CART_PAGE_SIZE

    Scaffold(
        topBar = {
            CartTopAppBar(
                onClick = { onClose() },
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        CartBody(
            innerPadding = innerPadding,
            cartItems = cartItems,
            currentPageIndex = state.currentPageIndex,
            lastPageIndex = lastPageIndex,
            onMoveToPreviousPage = { if (state.currentPageIndex > 0) state.decrease() },
            onMoveToNextPage = { if (state.currentPageIndex < lastPageIndex) state.increase() },
            onDeleteProduct = { id ->
                cartProducts.deleteProduct(id)

                val updatedProducts = cartProducts.getCartProducts()
                val updatedLastPageIndex =
                    if (updatedProducts.isEmpty()) 0 else (updatedProducts.size - 1) / CART_PAGE_SIZE

                state.adjustCurrentPage(
                    updatedLastPageIndex = updatedLastPageIndex,
                )
            },
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        cartProducts = InMemoryCartRepository(),
        onClose = {},
    )
}
