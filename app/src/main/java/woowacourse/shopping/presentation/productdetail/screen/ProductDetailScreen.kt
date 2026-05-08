package woowacourse.shopping.presentation.productdetail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import woowacourse.shopping.R
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.productdetail.ProductDetailUiEvent
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel
import woowacourse.shopping.presentation.productdetail.component.ActionButton
import woowacourse.shopping.presentation.productdetail.component.ProductDetail
import woowacourse.shopping.presentation.productdetail.component.ProductDetailTopAppBar
import woowacourse.shopping.presentation.productdetail.model.ProductUiModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    product: ProductUiModel,
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
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            ProductDetail(
                product = product,
                quantity = uiState.quantity,
                onQuantityIncrease = viewModel::increaseQuantity,
                onQuantityDecrease = viewModel::decreaseQuantity,
            )
            ActionButton(
                onClick = { viewModel.addToCart(product.productId) },
                text = "장바구니 담기",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        viewModel =
            ProductDetailViewModel(
                productRepository = AppContainer.productRepository,
                cartRepository = AppContainer.cartRepository,
            ),
        product =
            ProductUiModel(
                productId = Uuid.random(),
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = 99800,
            ),
        onClose = {},
    )
}
