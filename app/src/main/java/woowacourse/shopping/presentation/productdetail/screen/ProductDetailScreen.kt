package woowacourse.shopping.presentation.productdetail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import woowacourse.shopping.R
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.productdetail.ProductDetailUiEvent
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel
import woowacourse.shopping.presentation.productdetail.component.ActionButton
import woowacourse.shopping.presentation.productdetail.component.LastViewedProduct
import woowacourse.shopping.presentation.productdetail.component.ProductDetail
import woowacourse.shopping.presentation.productdetail.component.ProductDetailTopAppBar
import woowacourse.shopping.presentation.productdetail.model.ProductUiModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
    val lastViewedProduct = uiState.lastViewedProduct
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
            Column {
                ProductDetail(
                    product = product,
                    quantity = uiState.quantity,
                    onQuantityIncrease = viewModel::increaseQuantity,
                    onQuantityDecrease = viewModel::decreaseQuantity,
                )

                if (lastViewedProduct != null) {
                    Spacer(modifier = Modifier.height(20.dp))

                    LastViewedProduct(
                        product = lastViewedProduct,
                        onClick = { onLastViewedProductClick(lastViewedProduct) },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                    )
                }
            }
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

@Preview
@Composable
private fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        viewModel =
            ProductDetailViewModel(
                productRepository = AppContainer.productRepository,
                cartRepository = AppContainer.cartRepository,
                recentlyViewedProductRepository = AppContainer.recentlyViewedProductRepository,
                lastViewedProductRepository = AppContainer.lastViewedProductRepository,
            ),
        product =
            ProductUiModel(
                productId = 1,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = 99800,
            ),
        onLastViewedProductClick = {},
        onClose = {},
    )
}
