package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.presentation.productdetail.component.ActionButton
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ProductListContent(
    products: Products,
    productQuantities: Map<Uuid, Int>,
    hasNextPage: Boolean,
    onLoadMore: () -> Unit,
    onItemClick: (Product) -> Unit,
    onQuantityIncrease: (Product) -> Unit,
    onQuantityDecrease: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        items(
            items = products.productItems,
            key = { product -> product.productId },
        ) { product ->
            ProductItem(
                product = product,
                quantity = productQuantities[product.productId] ?: 0,
                onClick = { onItemClick(product) },
                onQuantityIncrease = { onQuantityIncrease(product) },
                onQuantityDecrease = { onQuantityDecrease(product.productId) },
            )
        }
        if (hasNextPage) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ActionButton(
                    onClick = onLoadMore,
                    text = "더보기",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
fun ProductListContentPreview() {
    ProductListContent(
        products = Products(emptyList()),
        productQuantities = emptyMap(),
        hasNextPage = true,
        onLoadMore = {},
        onItemClick = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
    )
}
