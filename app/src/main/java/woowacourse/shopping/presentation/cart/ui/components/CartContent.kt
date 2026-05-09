package woowacourse.shopping.presentation.cart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.presentation.cart.model.CartItemUiModel
import woowacourse.shopping.presentation.common.model.ProductUiModel

@Composable
fun CartContent(
    onDeleteItem: (Long) -> Unit,
    onIncrease: (Long) -> Unit,
    onDecrease: (Long) -> Unit,
    cartItems: ImmutableList<CartItemUiModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(
            items = cartItems,
            key = { it.product.id },
        ) { item ->
            val product = item.product
            CartCard(
                productName = product.name,
                price = item.totalPrice,
                imageUrl = product.imageUrl,
                quantity = item.quantity,
                onDeleteItem = {
                    onDeleteItem(product.id)
                },
                onIncrease = {
                    onIncrease(product.id)
                },
                onDecrease = {
                    onDecrease(product.id)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartContentPreview() {
    CartContent(
        onDeleteItem = {},
        onIncrease = {},
        onDecrease = {},
        cartItems =
            listOf(
                CartItemUiModel(
                    product =
                        ProductUiModel(
                            id = 1L,
                            name = "커피",
                            imageUrl = "",
                            price = 1000,
                        ),
                    quantity = 1,
                ),
                CartItemUiModel(
                    product =
                        ProductUiModel(
                            id = 2L,
                            name = "커피",
                            imageUrl = "",
                            price = 1000,
                        ),
                    quantity = 1,
                ),
            ).toImmutableList(),
    )
}
