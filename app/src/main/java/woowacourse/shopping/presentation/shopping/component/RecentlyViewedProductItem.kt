package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product

@Composable
fun RecentlyViewedProductItem(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        shape = RectangleShape,
        modifier =
            modifier
                .width(98.dp)
                .clickable { onClick() },
    ) {
        Column {
            ProductItemImage(
                product = product,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))
            ProductItemTitle(
                productName = product.productName,
            )
        }
    }
}

@Composable
private fun ProductItemImage(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.productName,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ProductItemTitle(
    productName: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = productName,
        color = Color.Black,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun RecentlyViewedProductItemPreview() {
    RecentlyViewedProductItem(
        product =
            Product(
                productId = 1,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                productName = "PET보틀-정사각형(370ml)",
                price = Price(10000),
            ),
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
    )
}
