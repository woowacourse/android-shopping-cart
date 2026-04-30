package woowacourse.shopping.feature.shopping.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.core.util.formattedPrice
import woowacourse.shopping.ui.theme.Gray50

@Composable
fun ProductCard(
    onClick: () -> Unit,
    imageUrl: String,
    productName: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "상품 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        )
        Text(
            text = productName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
        )
        Text(
            text = formattedPrice(price),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp,
            color = Gray50,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    ProductCard(
        imageUrl = "",
        productName = "커피",
        price = 1000,
        onClick = {},
    )
}
