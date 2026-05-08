package woowacourse.shopping.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.ui.component.QuantitySelector
import woowacourse.shopping.ui.component.ShoppingAppBar
import woowacourse.shopping.ui.theme.Gray40
import woowacourse.shopping.ui.theme.Green40
import woowacourse.shopping.ui.util.formattedPrice

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onCloseClick: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "닫기",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable { onCloseClick() },
                    )
                },
            )
        },
        bottomBar = {
            Box(
                modifier =
                    Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Green40)
                        .clickable { onAddToCart() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "장바구니 담기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        DetailContent(
            imageUrl = uiState.product.imageUrl,
            productName = uiState.product.name,
            quantity = uiState.quantity,
            totalPrice = uiState.totalPrice,
            onIncreaseQuantity = onIncreaseQuantity,
            onDecreaseQuantity = onDecreaseQuantity,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun DetailContent(
    imageUrl: String,
    productName: String,
    quantity: Int,
    totalPrice: Int,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "상품 이미지",
            placeholder = rememberVectorPainter(Icons.Default.CloudSync),
            error = rememberVectorPainter(Icons.Default.CloudOff),
            fallback = rememberVectorPainter(Icons.Default.CloudOff),
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f),
        )
        Text(
            text = productName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 18.dp),
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Gray40)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            Text(
                text = formattedPrice(totalPrice),
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                color = Color.Black,
            )
            QuantitySelector(
                onIncreaseQuantity = onIncreaseQuantity,
                onDecreaseQuantity = onDecreaseQuantity,
                quantity = quantity,
            )
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        uiState = DetailUiState(),
        onCloseClick = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
        onAddToCart = {},
    )
}

@Preview
@Composable
private fun DetailContentPreview() {
    DetailContent(
        imageUrl = "",
        productName = "Test",
        quantity = 1,
        totalPrice = 1000,
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
