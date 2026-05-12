package woowacourse.shopping.ui.common.component.recentlyviewed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.ShoppingTypography

@Composable
fun RecentlyViewedSection(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.recently_viewed_title),
            style = ShoppingTypography.sectionTitle,
        )
        Spacer(modifier = Modifier.size(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = products,
                key = { it.id.value.toString() },
            ) { product ->
                RecentlyViewedItem(
                    product = product,
                    onClick = { onProductClick(product) },
                )
            }
        }
    }
}

@Preview
@Composable
fun RecentlyViewedSectionPreview() {
    RecentlyViewedSection(
        products = InMemoryProductRepository.products.take(4),
        onProductClick = {},
    )
}
