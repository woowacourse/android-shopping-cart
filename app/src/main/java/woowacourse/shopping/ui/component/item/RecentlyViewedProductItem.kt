package woowacourse.shopping.ui.component.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.domain.Product

@Composable
fun RecentlyViewedProductItem(
    product: Product,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(100.dp)
                .height(120.dp)
                .clickable(
                    onClick = {
                        onClick(product)
                    },
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProductImage(
            imageUri = product.imageUri,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
        )
        Text(
            text = product.name,
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentlyViewedProductItemPreview() {
    RecentlyViewedProductItem(
        Product(
            id = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
            imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
            name = "너무너무너무너무너무너무너무너무 긴 이름",
            price = 1000,
        ),
        onClick = {},
    )
}
