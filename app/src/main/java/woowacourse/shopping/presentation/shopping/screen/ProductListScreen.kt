package woowacourse.shopping.presentation.shopping.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.shopping.ProductListStateHolder
import woowacourse.shopping.presentation.shopping.component.ProductListContent
import woowacourse.shopping.presentation.shopping.component.ProductListScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    stateHolder: ProductListStateHolder,
    onCartIconClick: () -> Unit,
    onItemClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    ProductListScaffold(
        onClick = onCartIconClick,
        modifier = modifier,
    ) {
        ProductListContent(
            products = stateHolder.products,
            hasNextPage = stateHolder.hasNextPage,
            onLoadMore = { stateHolder.loadMore() },
            onItemClick = { product -> onItemClick(product) },
        )
    }
}

@Preview
@Composable
private fun ProductListScreenPreview() {
    ProductListScreen(
        stateHolder =
            ProductListStateHolder(
                productRepository = ProductRepositoryImpl,
                initialPageIndex = 0,
                onPageIndexChanged = {},
            ),
        onCartIconClick = {},
        onItemClick = {},
    )
}
