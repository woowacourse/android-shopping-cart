package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.model.SimpleProductUiModel

@Composable
fun RecentProductList(
    recentProducts: List<SimpleProductUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.recent_products_title),
            fontSize = 16.sp,
            fontWeight = W700,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                key = { it.id },
                items = recentProducts,
            ) { product ->
                RecentProductItem(
                    imageUrl = product.imageUrl,
                    title = product.title,
                )
            }
        }
    }
}

@Composable
private fun RecentProductItem(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PreviewableAsyncImage(
            imageUrl = imageUrl,
            description = title,
        )

        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = W700,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun RecentProductListPreview() {
    RecentProductList(
        recentProducts = listOf(
            SimpleProductUiModel(
                title = "상품1",
                imageUrl = "",
                id = "1",
            ),
            SimpleProductUiModel(
                title = "상품2",
                imageUrl = "",
                id = "2",
            ),
        ),
    )
}
