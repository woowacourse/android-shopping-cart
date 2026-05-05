package woowacourse.shopping.presentation.cart.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.presentation.cart.component.CartProductItem
import woowacourse.shopping.presentation.cart.component.CartTopAppBar
import woowacourse.shopping.presentation.cart.component.Pagination
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    cart: Cart,
    currentPage: Int,
    hasMoreItems: Boolean,
    onPreviousPageClick: () -> Unit,
    onNextPageClick: () -> Unit,
    hasPreviousPage: Boolean,
    hasNextPage: Boolean,
    onDelete: (Uuid) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CartTopAppBar(
                onClick = onBack,
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            Column {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                ) {
                    items(cart.cartItems) { cartItem ->
                        CartProductItem(
                            cartItem = cartItem,
                            onDelete = onDelete,
                        )
                    }
                }
                if (hasMoreItems) {
                    Pagination(
                        onPreviousPageClick = onPreviousPageClick,
                        hasPreviousPage = hasPreviousPage,
                        currentPage = currentPage,
                        onNextPageClick = onNextPageClick,
                        hasNextPage = hasNextPage,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        cart = CartRepositoryImpl.getItems(),
        currentPage = 1,
        hasMoreItems = true,
        onPreviousPageClick = {},
        onNextPageClick = {},
        hasPreviousPage = true,
        hasNextPage = true,
        onDelete = {},
        onBack = {},
    )
}
