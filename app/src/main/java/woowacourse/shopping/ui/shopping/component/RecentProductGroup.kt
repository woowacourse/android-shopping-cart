package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products

@Composable
fun RecentProductGroup(
    products: Products,
    modifier: Modifier = Modifier,
    onRecentProductClick: (Product) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = products) {
        listState.scrollToItem(0)
    }

    Column(
        modifier = modifier.padding(top = 20.dp, bottom = 40.dp),
    ) {
        Text(
            text = "최근 본 상품",
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 20.dp),
        )

        Spacer(modifier = Modifier.size(8.dp))

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = products.toList(), key = { it.id }) { product ->
                RecentProductUnit(
                    product = product,
                    onClick = { onRecentProductClick(product) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentProductGroupPreview() {
    val product1 =
        Product(
            name = "소고기",
            price = Money(10000),
            imageUrl = "",
        )
    val product2 =
        Product(
            name = "돼지고기",
            price = Money(10000),
            imageUrl = "",
        )
    val product3 =
        Product(
            name = "양고기",
            price = Money(10000),
            imageUrl = "",
        )
    val product4 =
        Product(
            name = "닭고기",
            price = Money(10000),
            imageUrl = "",
        )
    val products = Products(listOf(product1, product2, product3, product4))
    RecentProductGroup(
        products = products,
        onRecentProductClick = {},
    )
}
