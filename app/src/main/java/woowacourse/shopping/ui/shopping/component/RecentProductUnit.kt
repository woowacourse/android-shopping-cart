package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.common.component.ShoppingImage

@Composable
fun RecentProductUnit(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .width(98.dp)
                .height(121.dp)
                .clickable(onClick = onClick),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ShoppingImage(
            model = product.imageUrl,
            contentDescription = "최근 본 상품 이미지",
            modifier =
                Modifier
                    .size(98.dp)
                    .align(Alignment.CenterHorizontally),
        )

        Text(
            text = product.name,
            color = Color(0xFF333333),
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentProductUnitPreview() {
    val product =
        Product(
            name = "소고기 질 좋아요 드셔보세요",
            price = Money(20000),
            imageUrl = "",
        )
    RecentProductUnit(
        product = product,
        onClick = {},
    )
}
