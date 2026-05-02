package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.ui.theme.dividerColor
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ProductDetail(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.productName,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
        Box(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {
            Text(
                text = product.productName,
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                color = Color.Black,
            )
        }
        HorizontalDivider(color = dividerColor, thickness = 1.dp)

        ProductPrice(
            price = product.price.value,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductDetailPreview() {
    ProductDetail(
        product =
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
    )
}
