package woowacourse.shopping.presentation.shopping.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.shopping.ProductListUiEvent
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ProductListUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    ProductListScaffold(
        totalQuantity = uiState.totalQuantity,
        snackbarHostState = { SnackbarHost(hostState = snackbarHostState) },
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
