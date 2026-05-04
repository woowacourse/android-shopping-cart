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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.theme.dividerColor
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun productDetail(
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

        productPrice(
            price = product.price.value,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun productDetailPreview() {
    val packageName = LocalContext.current.packageName

    productDetail(
        product = ProductFixture.productList(packageName).last(),
    )
}
