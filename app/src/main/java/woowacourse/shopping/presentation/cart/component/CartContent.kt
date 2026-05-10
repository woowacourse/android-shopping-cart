package woowacourse.shopping.presentation.cart.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product

@Composable
fun CartContent(
    cart: Cart,
    currentPage: Int,
    hasMoreItems: Boolean,
    onPreviousPageClick: () -> Unit,
    onNextPageClick: () -> Unit,
    hasPreviousPage: Boolean,
    hasNextPage: Boolean,
    onDelete: (Int) -> Unit,
    onQuantityIncrease: (Product) -> Unit,
    onQuantityDecrease: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(
                items = cart.cartItems,
                key = { cartItem -> cartItem.product.productId },
            ) { cartItem ->
                CartProductItem(
                    cartItem = cartItem,
                    onDelete = { onDelete(cartItem.product.productId) },
                    onQuantityIncrease = { onQuantityIncrease(cartItem.product) },
                    onQuantityDecrease = { onQuantityDecrease(cartItem.product.productId) },
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

@Preview
@Composable
fun CartContentPreview() {
    CartContent(
        cart = Cart(),
        currentPage = 1,
        hasMoreItems = true,
        onPreviousPageClick = {},
        onNextPageClick = {},
        hasPreviousPage = true,
        hasNextPage = true,
        onDelete = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
    )
}
