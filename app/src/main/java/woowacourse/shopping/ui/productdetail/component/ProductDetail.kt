package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ProductDetail(
    product: Product,
    quantity: Int,
    increaseQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.productName,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
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
            quantity = quantity,
            increaseQuantity = { increaseQuantity() },
            decreaseQuantity = { decreaseQuantity() },
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductDetailPreview() {
    val packageName = LocalContext.current.packageName

    ProductDetail(
        product = ProductFixture.productList(packageName).last(),
        quantity = 1,
        increaseQuantity = {},
        decreaseQuantity = {},
    )
}
