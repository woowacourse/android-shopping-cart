package woowacourse.shopping.presentation.productdetail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.theme.cartPageButtonColor
import woowacourse.shopping.presentation.theme.dividerColor
import woowacourse.shopping.presentation.theme.topAppBarColor

@Composable
fun LastViewedProduct(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = dividerColor,
                    shape = RoundedCornerShape(4.dp),
                ).padding(horizontal = 18.dp, vertical = 16.dp)
                .clickable(onClick = onClick),
    ) {
        Text(
            text = "마지막으로 본 상품",
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
            color = cartPageButtonColor,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.productName,
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
            color = topAppBarColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LastViewedProductPreview() {
    LastViewedProduct(
        product =
            Product(
                productId = 1,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
        onClick = {},
    )
}
