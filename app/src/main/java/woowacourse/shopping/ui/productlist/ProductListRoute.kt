package woowacourse.shopping.ui.productlist

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.ui.productlist.state.ProductListUiEvent
import woowacourse.shopping.ui.productlist.viewmodel.ProductListViewModel

@Composable
fun ProductListRoute(
    productListViewModel: ProductListViewModel = viewModel(factory = ProductListViewModel.Factory),
    onNavigateToDetail: (String) -> Unit,
    onNavigateToCart: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        productListViewModel.uiEvent.collect { event ->
            when (event) {
                is ProductListUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
