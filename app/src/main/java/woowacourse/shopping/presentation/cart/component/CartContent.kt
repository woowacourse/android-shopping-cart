package woowacourse.shopping.presentation.cart.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.domain.model.cart.Cart
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CartContent(
    cart: Cart,
    currentPage: Int,
    hasMoreItems: Boolean,
    onPreviousPageClick: () -> Unit,
    onNextPageClick: () -> Unit,
    hasPreviousPage: Boolean,
    hasNextPage: Boolean,
    onDelete: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
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

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
fun CartContentPreview() {
    CartContent(
        cart = AppContainer.cartRepository.getItems(),
        currentPage = 1,
        hasMoreItems = true,
        onPreviousPageClick = {},
        onNextPageClick = {},
        hasPreviousPage = true,
        hasNextPage = true,
        onDelete = {},
    )
}
