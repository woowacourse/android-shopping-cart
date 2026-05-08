package woowacourse.shopping.presentation.shopping.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.Money
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductName
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.presentation.common.model.toUiModel
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

@Composable
fun RecentSection(
    recentProducts: ImmutableList<ProductUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.recent_product),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(recentProducts.size) { index ->
                RecentProductCart(
                    product = recentProducts[index],
                )
            }
        }
        HorizontalDivider(
            thickness = 3.dp,
        )
    }
}

@Composable
private fun RecentProductCart(
    product: ProductUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxSize()
                    .aspectRatio(1f),
        )
        Text(
            text = product.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentSectionPreview() {
    val product =
        Product(
            id = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
            name = ProductName("아메리카노"),
            price = Money(6000),
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[106509]_20250626092521116.jpg",
        ).toUiModel()
    AndroidshoppingTheme {
        RecentSection(
            recentProducts = listOf(product).toImmutableList(),
        )
    }
}
