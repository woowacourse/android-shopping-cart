package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.cart.component.CartBody
import woowacourse.shopping.ui.cart.component.CartHeader
import woowacourse.shopping.ui.component.ShoppingLoading


@Composable
fun CartScreen(
    state: CartScreenState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    if (state.isLoading) {
        ShoppingLoading()
    } else {
        CartScreen(
            cart = Cart(state.pagedItems),
            currentPage = state.currentPage,
            totalPages = state.totalPages,
            showPagination = state.showPagination,
            modifier = modifier,
            onBackClick = onBackClick,
            onDeleteClick = { state.delete(it) },
            onPreviousClick = { state.previousPage() },
            onNextClick = { state.nextPage() },
        )
    }
}

@Composable
fun CartScreen(
    cart: Cart,
    currentPage: Int,
    totalPages: Int,
    showPagination: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDeleteClick: (Product) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        CartHeader(onBackClick = onBackClick)

        CartBody(
            cart = cart,
            showPagination = showPagination,
            currentPage = currentPage,
            totalPages = totalPages,
            modifier =
                Modifier
                    .padding(top = 8.dp, start = 18.dp, end = 18.dp)
                    .weight(1f),
            onDeleteClick = onDeleteClick,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
        )
    }
}

@Composable
@Preview(showBackground = true, name = "상품 5개 넘을 때")
private fun CartScreenPreview1() {
    val products = listOf(
        Product(name = "1번", price = Money(1000), imageUrl = ""),
        Product(name = "2번", price = Money(1000), imageUrl = ""),
        Product(name = "3번", price = Money(1000), imageUrl = ""),
        Product(name = "4번", price = Money(1000), imageUrl = ""),
        Product(name = "5번", price = Money(1000), imageUrl = ""),
    )
    val cart = Cart(products.map { CartItem(it, 1) })

    CartScreen(
        cart = cart,
        onBackClick = {},
        onDeleteClick = {},
        currentPage = 1,
        totalPages = 2,
        showPagination = true,
        modifier = Modifier,
        onPreviousClick = {},
        onNextClick = {},
    )
}

@Composable
@Preview(showBackground = true, name = "상품 없을 때")
private fun CartScreenPreview2() {
    val cart = Cart(emptyList())

    CartScreen(
        cart = cart,
        onBackClick = {},
        onDeleteClick = {},
        currentPage = 1,
        totalPages = 1,
        showPagination = false,
        modifier = Modifier,
        onPreviousClick = {},
        onNextClick = {},
    )
}
