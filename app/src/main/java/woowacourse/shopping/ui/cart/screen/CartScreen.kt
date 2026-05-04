package woowacourse.shopping.ui.cart.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import woowacourse.shopping.ui.cart.component.cartItem
import woowacourse.shopping.ui.cart.component.cartTopAppBar
import woowacourse.shopping.ui.cart.component.pagination
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun cartScreen(
    cartProducts: CartRepository,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentPageIndex by rememberSaveable { mutableStateOf(0) }
    var lastPageIndex =
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
                onClick = { onClose },
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            Column {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                ) {
                    items(CartRepository.getCartProducts()) { productAndCount ->
                        cartItem(
                            productAndCount = productAndCount,
                            onDelete = { id ->
                                CartRepository.deleteProduct(productId = id)

                                val updatedProducts = CartRepository.getCartProducts()
                                val updatedLastPageIndex =
                                    if (updatedProducts.isEmpty()) 0 else (updatedProducts.size - 1) / CART_PAGE_SIZE
                                if (currentPageIndex > updatedLastPageIndex) {
                                    currentPageIndex = updatedLastPageIndex
                                }
                            },
                        )
                    }
                }
                if (cartProducts.getCartProducts().size > 5) {
                    pagination(
                        pageMoveToLeft = { if (currentPageIndex > 0) currentPageIndex-- },
                        pageMoveToLeftButtonEnabled = currentPageIndex > 0,
                        currentPageIndex = currentPageIndex,
                        pageMoveToRight = { if (currentPageIndex < lastPageIndex) currentPageIndex++ },
                        pageMoveToRightButtonEnabled = currentPageIndex < lastPageIndex,
                    )
                }
            }
        }
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
