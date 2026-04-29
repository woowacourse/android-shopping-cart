package woowacourse.shopping.feature.detail.ui

import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.core.component.ShoppingAppBar
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.core.util.formattedPrice
import woowacourse.shopping.ui.theme.Gray40
import woowacourse.shopping.ui.theme.Green40

@Composable
fun DetailScreen(
    product: ProductUiModel,
    onAddProductToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "뒤로 가기",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable {
                                    activity?.finish()
                                },
                    )
                },
            )
        },
        bottomBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Green40)
                        .clickable {
                            onAddProductToCart()
                        },
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
            imageUrl = product.imageUrl,
            productName = product.name,
            price = product.price,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun DetailContent(
    imageUrl: String,
    productName: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "상품 이미지",
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
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Gray40)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "가격",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            )
            Text(
                text = formattedPrice(price),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 26.sp,
                color = Color.Black,
            )
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        onAddProductToCart = {},
        product =
            ProductUiModel(
                id = "id",
                imageUrl = "",
                name = "Test",
                price = 10000,
            ),
    )
}

@Preview
@Composable
private fun DetailContentPreview() {
    DetailContent(
        imageUrl = "",
        productName = "Test",
        price = 10000,
    )
}
