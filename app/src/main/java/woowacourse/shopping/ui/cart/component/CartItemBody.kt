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
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

@Composable
fun CartItemBody(
    cart: Cart,
    hasNext: Boolean,
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    onDeleteClick: (Product) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    val products = cart.items.keys

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = products.toList(), key = { it.id }) { product ->
            CartItemUnit(
                product = product,
                onDeleteClick = { onDeleteClick(product) }
            )
        }

        if (hasNext) {
            item {
                Spacer(modifier = Modifier.height(15.dp))

                CartPaging(
                    currentPage = currentPage,
                    totalPages = totalPages,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CartItemBodyPreview() {
    CartItemBody(
        cart = Cart(
            mapOf(
                Pair(InMemoryProductRepository.APPLE, 1),
                Pair(InMemoryProductRepository.BBOYAMI, 1)
            )
        ),
        onDeleteClick = {},
        hasNext = true,
        currentPage = 1,
        totalPages = 5,
        onPreviousClick = {},
        onNextClick = {}
    )
}
