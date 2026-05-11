package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product

@Composable
fun LastViewedProductBanner(
    lastViewedProduct: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .size(width = 324.dp, height = 80.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp),
                ).clickable(onClick = onClick)
                .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "마지막으로 본 상품",
            color = Color(0xFF04C09E),
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
        )

        Text(
            text = lastViewedProduct.name,
            color = Color(0xff555555),
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LastViewedProductBannerPreview() {
    val product =
        Product(
            name = "PET보틀-정사각형(500ml)",
            price = Money(8000),
            imageUrl = "",
        )
    LastViewedProductBanner(
        lastViewedProduct = product,
        onClick = {},
    )
}
