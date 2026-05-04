package woowacourse.shopping.ui.cart.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.domain.CART_PAGE_SIZE
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.CartRepository.deleteProduct
import woowacourse.shopping.ui.cart.component.CartBody
import woowacourse.shopping.ui.cart.component.cartTopAppBar
import woowacourse.shopping.ui.cart.state.CartState
import woowacourse.shopping.ui.cart.state.rememberCartState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun cartScreen(
    cartProducts: CartRepository,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberCartState()
    val lastPageIndex =
        if (cartProducts
                .getCartProducts()
                .isEmpty()
        ) {
            0
        } else {
            (cartProducts.getCartProducts().size - 1) / CART_PAGE_SIZE
        }

    Scaffold(
        topBar = {
            cartTopAppBar(
                onClick = { onClose() },
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        CartBody(
            innerPadding = innerPadding,
            cartProducts = cartProducts,
            currentPageIndex = state.currentPageIndex,
            lastPageIndex = lastPageIndex,
            onMoveToPreviousPage = { if (state.currentPageIndex > 0) state.decrease() },
            onMoveToNextPage = { if (state.currentPageIndex < lastPageIndex) state.increase() },
            onDeleteProduct = { id ->
                deleteProduct(id)

                val updatedProducts = cartProducts.getCartProducts()
                val updatedLastPageIndex =
                    if (updatedProducts.isEmpty()) 0 else (updatedProducts.size - 1) / CART_PAGE_SIZE

                state.adjustCurrentPage(
                    updatedLastPageIndex = updatedLastPageIndex
                )
            },
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun cartScreenPreview() {
    cartScreen(
        cartProducts = CartRepository,
        onClose = {},
    )
}
