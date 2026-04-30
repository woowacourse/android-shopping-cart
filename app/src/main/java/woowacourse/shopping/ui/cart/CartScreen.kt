package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.component.CartHeader
import woowacourse.shopping.ui.cart.component.CartItemBody

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
    val pagedItems = remember(cart, currentPage) {
        cart.items.toList()
            .drop((currentPage - 1) * PAGE_SIZE)
            .take(PAGE_SIZE)
            .toMap()
    }

    LaunchedEffect(totalPages) {
        currentPage = currentPage.coerceAtMost(totalPages)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CartHeader(onBackClick = onBackClick)

        CartItemBody(
            cart = Cart(pagedItems),
            showPagination = cartItemsSize >= PAGE_SIZE + 1,
            currentPage = currentPage,
            totalPages = totalPages,
            modifier = Modifier
                .padding(top = 8.dp, start = 18.dp, end = 18.dp)
                .weight(1f),
            onDeleteClick = onDeleteClick,
            onPreviousClick = { currentPage = (currentPage - 1).coerceAtLeast(1) },
            onNextClick = { currentPage = (currentPage + 1).coerceAtMost(totalPages) }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CartScreenPreview() {
    val cart = Cart(InMemoryProductRepository.products.associateWith { 1 })
    CartScreen(cart = cart, onBackClick = {}, onDeleteClick = {})
}
