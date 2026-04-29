package woowacourse.shopping.ui.component.item

import android.icu.text.DecimalFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.domain.Product
import java.util.UUID

@Composable
fun ShoppingItem(
    product: Product,
    onClick: (UUID) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(154.dp)
            .height(206.dp)
            .clickable(
                onClick = {
                    onClick(product.uuid)
                },
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ProductImage(product.imageUri, Modifier.size(154.dp))
        ProductInfo(product.name, product.price)
    }
}

@Composable
private fun ProductInfo(
    name: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 9.dp)
            .width(154.dp),
    ) {
        Text(
            text = name,
            fontWeight = FontWeight(700),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier,
        )

        Text(
            text = price.toPriceString(),
            fontSize = 16.sp,
            color = Color(0xFF555555),
            modifier = Modifier,
        )
    }
}

fun Int.toPriceString(): String {
    val formatter = DecimalFormat("###,###")
    return "${formatter.format(this)}원"
}

@Preview
@Composable
private fun ShoppingItemPreview() {
    ShoppingItem(
        Product(
            imageUri = "https://media.sodagift.com/img/image/1734582680547.jpg",
            name = "매우매우긴상품명입니다",
            price = 1000000000,
        ),
        onClick = {},
    )
}
