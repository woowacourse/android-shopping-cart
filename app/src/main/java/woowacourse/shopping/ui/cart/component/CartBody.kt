package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product

@Composable
fun CartBody(
    cart: Cart,
    showPagination: Boolean,
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    onDeleteClick: (CartItem) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onAddClick: (CartItem) -> Unit,
    onRemoveClick: (CartItem) -> Unit,
) {
    val cartItems = cart.items

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = cartItems, key = { it.product.id }) { cartItem ->
            CartItemUnit(
                cartItem = cartItem,
                onDeleteClick = { onDeleteClick(cartItem) },
                onAddClick = { onAddClick(cartItem) },
                onRemoveClick = { onRemoveClick(cartItem) },
            )
        }

        if (showPagination) {
            item {
                Spacer(modifier = Modifier.height(15.dp))

                CartPaging(
                    currentPage = currentPage,
                    totalPages = totalPages,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CartBodyPreview() {
    val product1 = Product(name = "1번", price = Money(1000), imageUrl = "")
    val product2 = Product(name = "2번", price = Money(1000), imageUrl = "")

    CartBody(
        cart =
            Cart(
                listOf(
                    CartItem(product1, 1),
                    CartItem(product2, 3),
                ),
            ),
        onDeleteClick = {},
        showPagination = true,
        currentPage = 1,
        totalPages = 5,
        onPreviousClick = {},
        onNextClick = {},
        onAddClick = {},
        onRemoveClick = {},
    )
}
