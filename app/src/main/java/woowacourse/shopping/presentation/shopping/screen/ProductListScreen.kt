package woowacourse.shopping.presentation.shopping.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.shopping.ProductListViewModel
import woowacourse.shopping.presentation.shopping.component.ProductListContent
import woowacourse.shopping.presentation.shopping.component.ProductListScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onCartIconClick: () -> Unit,
    onItemClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductListScaffold(
        totalQuantity = uiState.totalQuantity,
        onClick = onCartIconClick,
        modifier = modifier,
    ) {
        ProductListContent(
            products = uiState.products,
            recentlyViewedProducts = uiState.recentlyViewedProducts,
            productQuantities = uiState.productQuantities,
            hasNextPage = uiState.hasNextPage,
            onLoadMore = viewModel::loadMore,
            onItemClick = onItemClick,
            onQuantityIncrease = viewModel::increaseQuantity,
            onQuantityDecrease = viewModel::decreaseQuantity,
        )
    }
}

@Preview
@Composable
private fun ProductListScreenPreview() {
    ProductListScreen(
        viewModel =
            ProductListViewModel(
                productRepository = AppContainer.productRepository,
                cartRepository = AppContainer.cartRepository,
                recentlyViewedProductRepository = AppContainer.recentlyViewedProductRepository,
            ),
        onCartIconClick = {},
        onItemClick = {},
    )
}
