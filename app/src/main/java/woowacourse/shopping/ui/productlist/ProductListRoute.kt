package woowacourse.shopping.ui.productlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.productlist.viewmodel.ProductListViewModel

@Composable
fun ProductListRoute(
    productListViewModel: ProductListViewModel = viewModel(),
    onNavigateToDetail: (String) -> Unit,
    onCartIconClick: () -> Unit,
) {
    val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()

    ProductListScreen(
        productUiModels = uiState.products,
        recentProductUiModels = uiState.recentProducts,
        cartCount = uiState.cartCount,
        isEnd = uiState.isEnd,
        onProductClick = { id ->
            productListViewModel.onClickProduct(id)
            onNavigateToDetail(id)
        },
        onIncrement = { id ->
            productListViewModel.addCartItem(id)
        },
        onDecrement = { id ->
            productListViewModel.removeCartItem(id)
        },
        onCartIconClick = onCartIconClick,
        onLoading = { productListViewModel.fetchProducts() },
    )
}
