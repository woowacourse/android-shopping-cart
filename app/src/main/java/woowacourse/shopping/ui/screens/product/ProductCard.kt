package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.domain.Price
import woowacourse.shopping.ui.component.image.ShoppingImage
import woowacourse.shopping.ui.extension.toFormattedPrice

@Composable
fun ProductCard(
    imageUrl: String,
    name: String,
    price: Price,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ShoppingImage(
            imageUrl = imageUrl,
            contentDescription = "$name 이미지 입니다용",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )

        ProductInfoText(
            name = name,
            price = price,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}

@Composable
private fun ProductInfoText(
    name: String,
    price: Price,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Text(
            text = price.toFormattedPrice(),
            color = Color(0xff555555),
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    ProductCard(
        imageUrl =
            "https://cdn.eyesmag.com/content/uploads/posts/2024/10/23/shutterstock_250" +
                "0953971-3c494ea8-0ac0-4f8d-a962-e47db09215a0.jpg",
        name = "고양이",
        price = Price(999999999),
        onClick = { },
    )
}
