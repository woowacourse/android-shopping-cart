package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.shopping.component.ShoppingBody
import woowacourse.shopping.ui.shopping.component.ShoppingHeader

private const val PAGE_SIZE = 20

@Composable
fun ShoppingScreen(
    productRepo: ProductRepository,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
) {
    var visibleCount: Int by rememberSaveable { mutableIntStateOf(PAGE_SIZE) }
    val state = rememberShoppingScreenState(
        productRepo = productRepo,
        visibleCount = visibleCount,
    )

    val lazyGridState = rememberLazyGridState()

    if (!state.isLoading) {
        ShoppingScreen(
            products = state.visibleProducts,
            hasNext = state.hasNext,
            lazyGridState = lazyGridState,
            modifier = modifier,
            onCartClick = onCartClick,
            onProductClick = onProductClick,
            onMoreClick = {
                visibleCount = minOf(visibleCount + PAGE_SIZE, state.sizeInRepo)
                state.loadProducts(visibleCount)
            },
        )
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
    ShoppingScreen(
        products = InMemoryProductRepository.products,
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
