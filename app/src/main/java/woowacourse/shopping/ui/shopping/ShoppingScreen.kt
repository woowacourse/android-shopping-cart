package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.ui.component.ShoppingLoading
import woowacourse.shopping.ui.shopping.component.ShoppingBody
import woowacourse.shopping.ui.shopping.component.ShoppingHeader

@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        ShoppingScreen(
            products = Products(state.visibleProducts),
            hasNext = state.hasNext,
            lazyGridState = lazyGridState,
            onCartClick = onCartClick,
            onProductClick = onProductClick,
            onMoreClick = { viewModel.loadMore() },
        )

        if (state.isLoading) ShoppingLoading()
    }
}

@Composable
fun ShoppingScreen(
    products: Products,
    hasNext: Boolean,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShoppingHeader(onCartClick = onCartClick)

        ShoppingBody(
            products = products,
            showMoreButton = hasNext,
            lazyGridState = lazyGridState,
            modifier =
                Modifier
                    .padding(20.dp)
                    .weight(1f),
            onProductClick = onProductClick,
            onMoreClick = onMoreClick,
        )
    }
}

@Preview(showBackground = true, name = "상품 여러개")
@Composable
private fun ShoppingScreenPreview1() {
    val product1 =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )
    val product2 =
        Product(
            name = "연금복권",
            price = Money(1000),
            imageUrl = "",
        )
    val product3 =
        Product(
            name = "로또",
            price = Money(1000),
            imageUrl = "",
        )

    ShoppingScreen(
        products = Products(listOf(product1, product2, product3)),
        hasNext = true,
        lazyGridState = rememberLazyGridState(),
        onCartClick = {},
        onProductClick = {},
        onMoreClick = {},
    )
}

@Preview(showBackground = true, name = "상품 0개")
@Composable
private fun ShoppingScreenPreview2() {
    ShoppingScreen(
        products = Products(emptyList()),
        hasNext = false,
        lazyGridState = rememberLazyGridState(),
        onCartClick = {},
        onProductClick = {},
        onMoreClick = {},
    )
}
