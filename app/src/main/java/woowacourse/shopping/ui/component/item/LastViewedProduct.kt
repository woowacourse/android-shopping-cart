package woowacourse.shopping.ui.component.item

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.domain.Product

@Composable
fun LastViewedProduct(
    product: Product,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFAAAAAA),
                ).clickable(
                    onClick = { onClick(product) },
                ).padding(18.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "마지막으로 본 상품",
            color = Color(0xFF04C09E),
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
        )
        Text(
            text = product.name,
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
            color = Color(0xFF555555),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun LastViewedProductPreview() {
    LastViewedProduct(
        Product(
            imageUri = "테스트",
            name = "테스트",
            price = 1000,
        ),
        onClick = { },
    )
}
