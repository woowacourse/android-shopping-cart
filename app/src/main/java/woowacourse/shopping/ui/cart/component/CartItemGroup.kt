package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

@Composable
fun CartItemGroup(
    cart: Cart,
    modifier: Modifier = Modifier,
    onDeleteClick: (Product) -> Unit,
) {
    val products = cart.items.keys
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(items = products.toList(), key = { it.id }) { product ->
            CartItemUnit(
                product = product,
                onDeleteClick = { onDeleteClick(product) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CartItemGroupPreview() {
    CartItemGroup(
        cart = Cart(
            mapOf(
                Pair(InMemoryProductRepository.APPLE, 1),
                Pair(InMemoryProductRepository.BBOYAMI, 1)
            )
        ),
        onDeleteClick = {}
    )
}
