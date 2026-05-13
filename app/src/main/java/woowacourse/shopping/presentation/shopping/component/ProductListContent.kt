package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts
import woowacourse.shopping.presentation.productdetail.component.ActionButton
import woowacourse.shopping.presentation.theme.homeDividerColor

@Composable
fun ProductListContent(
    products: Products,
    recentlyViewedProducts: RecentlyViewedProducts,
    productQuantities: Map<Int, Int>,
    hasNextPage: Boolean,
    onLoadMore: () -> Unit,
    onItemClick: (Product) -> Unit,
    onQuantityIncrease: (Int) -> Unit,
    onQuantityDecrease: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        recentlyViewedProductSection(
            products = recentlyViewedProducts,
            onItemClick = onItemClick,
        )
        productItems(
            products = products,
            productQuantities = productQuantities,
            onItemClick = onItemClick,
            onQuantityIncrease = onQuantityIncrease,
            onQuantityDecrease = onQuantityDecrease,
        )
        loadMoreButton(
            hasNextPage = hasNextPage,
            onLoadMore = onLoadMore,
        )
    }
}

private fun LazyGridScope.recentlyViewedProductSection(
    products: RecentlyViewedProducts,
    onItemClick: (Product) -> Unit,
) {
    if (products.productItems.isEmpty()) return

    item(span = { GridItemSpan(maxLineSpan) }) {
        RecentlyViewedProductsSection(
            products = products,
            onClick = onItemClick,
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 20.dp),
        )
    }
    item(span = { GridItemSpan(maxLineSpan) }) {
        HorizontalDivider(
            thickness = 7.dp,
            color = homeDividerColor,
        )
    }
}

private fun LazyGridScope.productItems(
    products: Products,
    productQuantities: Map<Int, Int>,
    onItemClick: (Product) -> Unit,
    onQuantityIncrease: (Int) -> Unit,
    onQuantityDecrease: (Int) -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Spacer(modifier = Modifier.height(8.dp))
    }

    itemsIndexed(
        items = products.productItems,
        key = { _, product -> product.productId },
    ) { index, product ->
        val isLeftColumn = index % 2 == 0
        ProductItem(
            product = product,
            quantity = productQuantities[product.productId] ?: 0,
            onClick = { onItemClick(product) },
            onQuantityIncrease = { onQuantityIncrease(product.productId) },
            onQuantityDecrease = { onQuantityDecrease(product.productId) },
            modifier =
                Modifier.padding(
                    start = if (isLeftColumn) 20.dp else 0.dp,
                    end = if (isLeftColumn) 0.dp else 20.dp,
                ),
        )
    }
}

private fun LazyGridScope.loadMoreButton(
    hasNextPage: Boolean,
    onLoadMore: () -> Unit,
) {
    if (!hasNextPage) return

    item(span = { GridItemSpan(maxLineSpan) }) {
        ActionButton(
            onClick = onLoadMore,
            text = "더보기",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
        )
    }
}

@Preview
@Composable
fun ProductListContentPreview() {
    ProductListContent(
        products = Products(emptyList()),
        recentlyViewedProducts = RecentlyViewedProducts(emptyList()),
        productQuantities = emptyMap(),
        hasNextPage = true,
        onLoadMore = {},
        onItemClick = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
    )
}
