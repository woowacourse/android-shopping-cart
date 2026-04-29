package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.component.CartHeader
import woowacourse.shopping.ui.cart.component.CartItemGroup
import woowacourse.shopping.ui.cart.component.CartPaging

private const val PAGE_SIZE = 5

@Composable
fun CartScreen(
    cart: Cart,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDeleteClick: (Product) -> Unit
) {
    val cartItemsSize = cart.items.size
    val totalPages = (cartItemsSize - 1) / PAGE_SIZE + 1

    var currentPage by remember { mutableIntStateOf(1) }
    val pagedItems = cart.items.toList()
        .drop((currentPage - 1) * PAGE_SIZE)
        .take(PAGE_SIZE)
        .toMap()


    LaunchedEffect(totalPages) {
        currentPage = currentPage.coerceAtMost(totalPages)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CartHeader(onBackClick = onBackClick)

        CartItemGroup(
            cart = Cart(pagedItems),
            modifier = Modifier
                .padding(top = 8.dp, start = 18.dp, end = 18.dp)
                .weight(1f),
            onDeleteClick = onDeleteClick
        )

        if (cartItemsSize >= PAGE_SIZE) {
            Spacer(modifier = Modifier.height(60.dp))

            CartPaging(
                currentPage = currentPage,
                totalPages = totalPages,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onPreviousClick = { currentPage -= 1 },
                onNextClick = { currentPage += 1 }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CartScreenPreview() {
    val cart = Cart(InMemoryProductRepository.products.associateWith { 1 })
    CartScreen(cart = cart, onBackClick = {}, onDeleteClick = {})
}
