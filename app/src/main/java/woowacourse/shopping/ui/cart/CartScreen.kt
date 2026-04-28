package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.component.CartHeader
import woowacourse.shopping.ui.cart.component.CartItemGroup

@Composable
fun CartScreen(
    cart: Cart,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDeleteClick: (Product) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CartHeader(onBackClick = onBackClick)

        Box(
            modifier = Modifier.padding(top = 24.dp, start = 18.dp, end = 18.dp)
        ) {
            CartItemGroup(
                cart = cart,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CartScreenPreview() {
    CartScreen(
        cart = Cart(
            mapOf(
                Pair(InMemoryProductRepository.APPLE, 1),
                Pair(InMemoryProductRepository.BBOYAMI, 1)
            )
        ),
        onBackClick = {},
        onDeleteClick = {}
    )
}
