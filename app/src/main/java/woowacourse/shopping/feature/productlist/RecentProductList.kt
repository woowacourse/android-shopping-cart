package woowacourse.shopping.feature.productlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.feature.common.state.ProductUiModel

@Composable
fun RecentProductList(
    recentProducts: List<ProductUiModel>,
    onRecentProductClick: (ProductUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text("최근 본 상품", fontWeight = FontWeight.W700, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(
                items = recentProducts,
            ) {
                Column {
                    PreviewableAsyncImage(
                        imageUrl = it.imageUrl,
                        description = it.name,
                        modifier = Modifier
                            .size(98.dp)
                            .clickable(
                                onClick = {
                                    onRecentProductClick(it)
                                },
                            ),
                    )
                    Text(
                        it.name,
                        fontWeight = FontWeight.W700,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecentProductListPreview() {
    RecentProductList(
        recentProducts = MockData.MOCK_PRODUCTS.map {
            ProductUiModel(
                name = it.name,
                price = 1000,
                imageUrl = it.imageUrl,
                id = it.id,
                quantity = 0,
            )
        },
        onRecentProductClick = {},
    )
}
