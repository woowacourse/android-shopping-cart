package woowacourse.shopping.presentation.productdetail.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.productdetail.ProductDetailUiEvent
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel
import woowacourse.shopping.presentation.productdetail.component.ProductDetailContent
import woowacourse.shopping.presentation.productdetail.component.ProductDetailTopAppBar
import woowacourse.shopping.presentation.productdetail.model.ProductUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    product: ProductUiModel,
    onLastViewedProductClick: (Product) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ProductDetailUiEvent.ShowMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = { ProductDetailTopAppBar(onClose) },
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        ProductDetailContent(
            product = product,
            uiState = uiState,
            onQuantityIncrease = viewModel::increaseQuantity,
            onQuantityDecrease = viewModel::decreaseQuantity,
            onLastViewedProductClick = onLastViewedProductClick,
            onAddToCart = viewModel::addToCart,
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        )
    }
}
