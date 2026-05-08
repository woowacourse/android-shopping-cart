package woowacourse.shopping.ui.productlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.productlist.viewmodel.ProductListViewModel

@Composable
fun ProductListRoute(
    productListViewModel: ProductListViewModel = viewModel(),
    onProductClick: (String) -> Unit,
    onCartIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()

    ProductListScreen(
        productUiModels = uiState.products,
        recentProductUiModels = uiState.recentProducts,
        cartCount = uiState.cartCount,
        isEnd = uiState.isEnd,
        onProductClick = onProductClick,
        onIncrement = {},
        onDecrement = {},
        onCartIconClick = onCartIconClick,
        onLoading = {},
    )
}
