package woowacourse.shopping.ui.shopping.component

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.ProductDetailActivity
import woowacourse.shopping.R
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.theme.topAppBarColor
import woowacourse.shopping.util.intFormatter
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        shape = RectangleShape,
        modifier = modifier.clickable{
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra("woowacourse.shopping.product_id", product.productId.toString())
            }
            context.startActivity(intent)
        },
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.productName,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(6.dp),
            ) {
                Text(
                    text = product.productName,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${intFormatter(product.price.value)}원",
                    color = topAppBarColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                )
            }
        }
    }
}


@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ProductItemPreview() {
    ProductItem(
        product =
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                productName = "PET보틀-정사각형(370ml)",
                price = Price(10000),
            ),
        modifier = Modifier.fillMaxWidth(),
    )
}
