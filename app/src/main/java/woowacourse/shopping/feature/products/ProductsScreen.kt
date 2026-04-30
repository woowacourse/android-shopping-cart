package woowacourse.shopping.feature.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.feature.products.component.LoadButton
import woowacourse.shopping.feature.products.component.ProductItem
import woowacourse.shopping.feature.products.component.ProductsTopAppBar
import woowacourse.shopping.feature.products.model.ShoppingProductInfo

@Composable
fun ProductsScreen(
    products: ImmutableList<ShoppingProductInfo>,
    isLastPage: Boolean,
    onCartClick: () -> Unit,
    onProductClick: (id: String) -> Unit,
    onLoadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ProductsTopAppBar(onClick = onCartClick)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = products,
                key = { it.id }
            ) {
                ProductItem(
                    productImageUrl = it.productImageUrl,
                    productName = it.productName,
                    price = it.price,
                    onClick = {onProductClick(it.id)}
                )
            }

            item(span = { GridItemSpan(2) }) {
                if (!isLastPage) LoadButton(onClick = onLoadClick)
            }
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val products = List(50) {
        ShoppingProductInfo(
            id = it.toString(),
            productImageUrl = "",
            productName = "$it 번 상품",
            price = "$it 원",
        )
    }.toImmutableList()

    ProductsScreen(
        products = products,
        isLastPage = false,
        onCartClick = {},
        onProductClick = {},
        onLoadClick = {}
    )
}

