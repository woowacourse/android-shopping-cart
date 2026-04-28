package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

@Composable
fun ProductGroup(
    products: Products,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 154.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = products.toList(), key = { it.id }) { product ->
            ProductUnit(product, onClick = { onProductClick(product) })
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductGroupPreview() {
    ProductGroup(
        products = InMemoryProductRepository.products,
        onProductClick = {},
    )
}
