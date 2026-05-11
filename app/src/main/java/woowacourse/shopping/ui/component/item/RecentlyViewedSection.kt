package woowacourse.shopping.ui.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.domain.Product
import java.util.UUID

@Composable
fun RecentlyViewedSection(
    recentProducts: List<Product>,
    onItemClick: (UUID) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (recentProducts.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "최근 본 상품",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = recentProducts.size,
                key = { index -> recentProducts[index].productId }
            ) { index ->
                RecentItem(
                    product = recentProducts[index],
                    onClick = onItemClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecentlyViewedSectionPreview() {
    RecentlyViewedSection(
        recentProducts = listOf<Product>(
            Product(
                imageUri = "https://media.sodagift",
                name = "매우매우긴상품명입니다",
                price = 100000000,
            ),
            Product(
                imageUri = "https://media.sodagift",
                name = "매우매우긴상품명입니다",
                price = 100000000,
            ),
            Product(
                imageUri = "https://media.sodagift",
                name = "매우매우긴상품명입니다",
                price = 100000000,
            ),
            Product(
                imageUri = "https://media.sodagift",
                name = "매우매우긴상품명입니다",
                price = 100000000,
            ),
            Product(
                imageUri = "https://media.sodagift",
                name = "매우매우긴상품명입니다",
                price = 100000000,
            ),
            Product(
                imageUri = "https://media.sodagift",
                name = "매우매우긴상품명입니다",
                price = 100000000,
            )
        ),
        onItemClick = { },
    )
}
