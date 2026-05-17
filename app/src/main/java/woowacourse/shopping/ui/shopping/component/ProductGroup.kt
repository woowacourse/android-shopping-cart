package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.common.model.ProductUiModel

@Composable
fun ProductGroup(
    products: List<ProductUiModel>,
    showMoreButton: Boolean,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit,
    onIncreaseClick: (Product) -> Unit,
    onDecreaseClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 154.dp),
        modifier = modifier,
        state = lazyGridState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = products, key = { it.product.id }) { productModel ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProductUnit(
                    model = productModel,
                    onClick = { onProductClick(productModel.product) },
                    onIncreaseClick = onIncreaseClick,
                    onDecreaseClick = onDecreaseClick,
                )
            }
        }

        if (showMoreButton) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    MoreButton(
                        modifier = Modifier,
                        onClick = onMoreClick,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductGroupPreview() {
    val product1 =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )
    val product2 =
        Product(
            name = "연금복권",
            price = Money(1000),
            imageUrl = "",
        )
    val product3 =
        Product(
            name = "로또",
            price = Money(1000),
            imageUrl = "",
        )

    ProductGroup(
        products = listOf(product1, product2, product3).map { ProductUiModel(it) },
        showMoreButton = true,
        lazyGridState = rememberLazyGridState(),
        onProductClick = {},
        onMoreClick = {},
        onIncreaseClick = {},
        onDecreaseClick = {},
    )
}
