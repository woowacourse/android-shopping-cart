package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import woowacourse.shopping.ui.component.ProductAsyncImage
import woowacourse.shopping.ui.model.ProductUiModel

@Composable
fun RecentItemsSection(
    recentItems: ImmutableList<ProductUiModel>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "최근 본 상품",
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 26.sp,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState()),
        ) {
            recentItems.forEach { product ->
                RecentItemCard(
                    product = product,
                    onClick = { onProductClick(product.id) },
                )
            }
        }
    }
}

@Composable
private fun RecentItemCard(
    product: ProductUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(98.dp)
                .clickable { onClick() },
    ) {
        ProductAsyncImage(
            imageUrl = product.imageUrl,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
        )

        Text(
            text = product.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentItemsSectionPreview() {
    RecentItemsSection(
        recentItems =
            persistentListOf(
                ProductUiModel(),
                ProductUiModel(),
                ProductUiModel(),
            ),
        onProductClick = {},
    )
}
