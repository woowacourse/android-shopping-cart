package woowacourse.shopping.ui.screens.productdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.domain.Price
import woowacourse.shopping.ui.component.counter.QuantityCounter
import woowacourse.shopping.ui.component.image.ShoppingImage
import woowacourse.shopping.ui.component.topbar.DismissTopBar
import woowacourse.shopping.ui.extension.toFormattedPrice

@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: ProductDetailViewModel = viewModel(
        factory = ProductDetailViewModel.factory(
            productId,
        ),
    ),
    onLastViewProductClick: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val product = state.product ?: return

    Scaffold(
        topBar = {
            DismissTopBar(
                onDismiss = onDismiss,
            )
        },
        modifier = Modifier
            .systemBarsPadding(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
        ) {
            ShoppingImage(
                imageUrl = product.imageUrl,
                contentDescription = "${product.name} 이미지",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProductInfoText(
                name = product.name,
                price = product.price,
                quantity = state.quantity,
                onPlusClick = { viewModel.plusCartCount() },
                onMinusClick = { viewModel.minusCartCount() },
            )

            Spacer(modifier = Modifier.height(29.dp))

            state.recentProduct?.let {
                if (productId != it.id) {
                    LastViewProductCard(
                        name = it.name,
                        onClick = { onLastViewProductClick(it.id) },
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                            .fillMaxWidth(),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            AddCartButton(
                onClick = { viewModel.addToCart() },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ProductInfoText(
    name: String,
    price: Price,
    quantity: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(17.dp),
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(horizontal = 18.dp),
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFAAAAAA),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = (price * quantity).toFormattedPrice(),
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
            )

            QuantityCounter(
                quantity = quantity,
                onMinusClick = { onMinusClick() },
                onPlusClick = { onPlusClick() },
            )
        }
    }
}

@Composable
private fun AddCartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF04C09E),
            contentColor = Color.White,
        ),
        modifier = modifier,
    ) {
        Text(
            text = "장바구니 담기",
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier
                .padding(vertical = 12.dp),
        )
    }
}

@Preview
@Composable
private fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        productId = "",
        onDismiss = { },
        onLastViewProductClick = { },
    )
}
