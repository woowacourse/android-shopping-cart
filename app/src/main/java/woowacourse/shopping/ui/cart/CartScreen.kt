package woowacourse.shopping.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.cart.component.CartHeader
import woowacourse.shopping.ui.cart.component.CartItemBody

private const val PAGE_SIZE = 5

@Composable
fun CartScreen(
    cartRepo: CartRepository,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val state = rememberCartScreenState(cartRepo = cartRepo)

    var currentPage by rememberSaveable { mutableIntStateOf(1) }
    val totalPages = (state.cart.items.size - 1) / PAGE_SIZE + 1

    LaunchedEffect(totalPages) {
        currentPage = currentPage.coerceAtMost(totalPages)
    }

    val pagedItems =
        remember(state.cart, currentPage) {
            state.cart.items
                .toList()
                .drop((currentPage - 1) * PAGE_SIZE)
                .take(PAGE_SIZE)
                .toMap()
        }

    CartScreen(
        cart = Cart(pagedItems),
        currentPage = currentPage,
        totalPages = totalPages,
        showPagination = state.cart.items.size >= PAGE_SIZE + 1,
        modifier = modifier,
        onBackClick = onBackClick,
        onDeleteClick = { state.delete(it) },
        onPreviousClick = { currentPage = (currentPage - 1).coerceAtLeast(1) },
        onNextClick = { currentPage = (currentPage + 1).coerceAtMost(totalPages) }
    )
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

        CartItemBody(
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
fun rememberCartScreenState(
    cartRepo: CartRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): CartScreenState =
    remember(cartRepo, coroutineScope) { CartScreenState(cartRepo, coroutineScope) }

@Composable
@Preview(showBackground = true)
private fun CartScreenPreview() {
    val cart = Cart(InMemoryProductRepository.products.associateWith { 1 })
    CartScreen(
        cart = cart,
        onBackClick = {},
        onDeleteClick = {},
        currentPage = 1,
        totalPages = 5,
        showPagination = true,
        modifier = Modifier,
        onPreviousClick = {},
        onNextClick = {}
    )
}
