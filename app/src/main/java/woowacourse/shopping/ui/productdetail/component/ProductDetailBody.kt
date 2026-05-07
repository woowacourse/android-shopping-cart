package woowacourse.shopping.ui.productdetail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.component.ShoppingImage

@Composable
fun ProductDetailBody(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ShoppingImage(
            model = product.imageUrl,
            contentDescription = "상품 상세 이미지",
            modifier = Modifier.height(360.dp),
        )
        ProductLabel(product)
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ProductLabel(
    product: Product,
    modifier: Modifier = Modifier,
) {
    val price = product.price.value
    val formatted = String.format("%,d", price)

    Column(modifier = modifier) {
        Text(
            text = product.name,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 18.dp, top = 16.dp, bottom = 17.dp),
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "가격",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.W400,
            )
            Text(
                text = "$formatted 원",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.W400,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, name = "상품 유닛")
private fun ProductUnitPreview() {
    val product =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )
    ProductDetailBody(product)
}

@Composable
@Preview(showBackground = true, name = "상품 이름만")
private fun ProductLabelPreview() {
    val product =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )
    ProductLabel(product = product)
}
