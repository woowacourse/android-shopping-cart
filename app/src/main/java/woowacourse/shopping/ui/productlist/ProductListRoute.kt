package woowacourse.shopping.ui.productlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.productlist.viewmodel.ProductListViewModel

@Composable
fun ProductListRoute(
    productListViewModel: ProductListViewModel = viewModel(factory = ProductListViewModel.Factory),
    onNavigateToDetail: (String) -> Unit,
    onNavigateToCart: () -> Unit,
) {
    val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()

    ProductListScreen(
        productUiModels = uiState.products,
        recentProductUiModels = uiState.recentProducts,
        cartCount = uiState.cartCount,
        isEnd = uiState.isEnd,
        onProductClick = { id ->
            onNavigateToDetail(id)
        },
        onIncrement = { id ->
            productListViewModel.addCartItem(id)
        },
        onDecrement = { id ->
            productListViewModel.removeCartItem(id)
        },
        onCartIconClick = onNavigateToCart,
        onLoading = { productListViewModel.fetchProducts() },
    )
}
