package woowacourse.shopping.ui.shopping.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.ShoppingTypography

@SuppressLint("DefaultLocale")
@Composable
fun ProductUnit(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val price = product.price.value
    val formatted = String.format("%,d", price)
    Column(
        modifier =
            modifier
                .width(154.dp)
                .height(206.dp)
                .clickable(onClick = onClick),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "이미지",
            modifier = Modifier.size(154.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.size(6.dp))
        Text(
            text = product.name,
            color = Color.Black,
            style = ShoppingTypography.productName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 6.dp, end = 9.dp),
        )
        Text(
            text = "$formatted 원",
            color = Color.DarkGray,
            style = ShoppingTypography.productPrice,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductUnitPreview() {
    ProductUnit(product = InMemoryProductRepository.APPLE, onClick = {})
}

@Composable
@Preview(showBackground = true, name = "긴 이름을 가진 상품")
private fun ProductUnitPreview2() {
    ProductUnit(
        product =
            Product(
                name = "정말정말 엄청나게 긴 이름을 가지고 있는 상품",
                price = Money(1000),
                imageUrl = "",
            ),
        onClick = {},
    )
}
