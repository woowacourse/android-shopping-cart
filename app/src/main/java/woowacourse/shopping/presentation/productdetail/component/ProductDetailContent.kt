package woowacourse.shopping.presentation.productdetail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.productdetail.ProductDetailUiState
import woowacourse.shopping.presentation.productdetail.model.ProductUiModel

@Composable
fun ProductDetailContent(
    product: ProductUiModel,
    uiState: ProductDetailUiState,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit,
    onLastViewedProductClick: (Product) -> Unit,
    onAddToCart: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lastViewedProduct = uiState.lastViewedProduct

    Box(
        modifier = modifier,
    ) {
        Column {
            ProductDetail(
                product = product,
                quantity = uiState.quantity,
                onQuantityIncrease = onQuantityIncrease,
                onQuantityDecrease = onQuantityDecrease,
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
            onClick = { onAddToCart(product.productId) },
            text = "장바구니 담기",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
        )
    }
}

@Preview
@Composable
private fun ProductDetailContentPreview() {
    ProductDetailContent(
        product =
            ProductUiModel(
                productId = 1,
                imageUrl = "",
                productName = "[든든] 동원 스위트콘",
                price = 99800,
            ),
        uiState =
            ProductDetailUiState(
                quantity = 1,
                lastViewedProduct = null,
            ),
        onQuantityIncrease = {},
        onQuantityDecrease = {},
        onLastViewedProductClick = {},
        onAddToCart = {},
    )
}
