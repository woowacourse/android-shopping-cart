package woowacourse.shopping.feature.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.core.designsystem.component.NetworkImage
import woowacourse.shopping.feature.productDetail.component.AddCartButton
import woowacourse.shopping.feature.productDetail.component.ProductDetailTopAppBar
import woowacourse.shopping.feature.productDetail.model.ProductInfo

@Composable
fun ProductDetailScreen(
    productInfo: ProductInfo,
    onCloseClick: () -> Unit,
    onAddCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.White),
    ) {
        ProductDetailTopAppBar(onClick = onCloseClick)

        NetworkImage(
            imageUrl = productInfo.productImageUrl,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = productInfo.productName,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(16.dp))

        HorizontalDivider(
            thickness = Dp.Hairline,
            color = Color.LightGray,
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "가격",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                maxLines = 1,
            )

            Text(
                text = productInfo.price,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                maxLines = 1,
            )
        }

        Spacer(Modifier.weight(1f))

        AddCartButton(onClick = onAddCartClick)
    }
}

@Preview
@Composable
private fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        productInfo = ProductInfo.PREVIEW,
        onCloseClick = {},
        onAddCartClick = {},
    )
}
