package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products

@Composable
fun RecentProducts(
    products: Products,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(start = 20.dp, top = 20.dp, bottom = 40.dp),
    ) {
        Text(
            text = "최근 본 상품",
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 26.67.sp,
        )

        Spacer(modifier = Modifier.size(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = products.toList(), key = { it.id }) { product ->
                RecentProductUnit(
                    product = product
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentProductsPreview() {
    val product1 = Product(
        name = "소고기",
        price = Money(10000),
        imageUrl = ""
    )
    val product2 = Product(
        name = "돼지고기",
        price = Money(10000),
        imageUrl = ""
    )
    val product3 = Product(
        name = "양고기",
        price = Money(10000),
        imageUrl = ""
    )
    val product4 = Product(
        name = "닭고기",
        price = Money(10000),
        imageUrl = ""
    )
    val products = Products(listOf(product1, product2, product3, product4))
    RecentProducts(
        products = products
    )
}
