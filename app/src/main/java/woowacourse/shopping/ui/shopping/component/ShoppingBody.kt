package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

@Composable
fun ShoppingBody(
    products: Products,
    showMoreButton: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 154.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = products.toList(), key = { it.id }) { product ->
            ProductUnit(product, onClick = { onProductClick(product) })
        }

        if(isLoading && products.toList().isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (!isLoading && showMoreButton) {
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
private fun ShoppingBodyPreview() {
    ShoppingBody(
        products = InMemoryProductRepository.products,
        showMoreButton = true,
        isLoading = true,
        onProductClick = {},
        onMoreClick = {},
    )
}
