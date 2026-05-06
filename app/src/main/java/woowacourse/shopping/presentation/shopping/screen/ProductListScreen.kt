package woowacourse.shopping.presentation.shopping.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.presentation.shopping.component.ProductListContent
import woowacourse.shopping.presentation.shopping.component.ProductListScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: Products,
    hasNextPage: Boolean,
    onLoadMore: () -> Unit,
    onCartIconClick: () -> Unit,
    onItemClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    ProductListScaffold(
        onClick = onCartIconClick,
        modifier = modifier,
    ) {
        ProductListContent(
            products = products,
            hasNextPage = hasNextPage,
            onLoadMore = onLoadMore,
            onItemClick = { product -> onItemClick(product) },
        )
    }
}

@Preview
@Composable
private fun ProductListScreenPreview() {
    ProductListScreen(
        products = Products(emptyList()),
        hasNextPage = true,
        onLoadMore = {},
        onCartIconClick = {},
        onItemClick = {},
    )
}
