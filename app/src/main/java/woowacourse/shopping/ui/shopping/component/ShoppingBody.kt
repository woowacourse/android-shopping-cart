package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.common.component.recentlyviewed.RecentlyViewedSection
import woowacourse.shopping.ui.shopping.ShoppingProductUiState
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun ShoppingBody(
    products: List<ShoppingProductUiState>,
    recentProducts: List<Product>,
    showMoreButton: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
    onAddToCart: (ProductId) -> Unit,
    onIncreaseQuantity: (ProductId) -> Unit,
    onDecreaseQuantity: (ProductId) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 154.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (recentProducts.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                RecentlyViewedSection(
                    products = recentProducts,
                    onProductClick = onProductClick,
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .background(color = ShoppingColors.Gray1),
                )
            }
        }

        items(items = products, key = {
            it.product.id.value
                .toString()
        }) { product ->
            ProductUnit(
                product = product,
                onClick = { onProductClick(product.product) },
                onAddToCart = { onAddToCart(product.product.id) },
                onIncreaseQuantity = { onIncreaseQuantity(product.product.id) },
                onDecreaseQuantity = { onDecreaseQuantity(product.product.id) },
            )
        }

        if (isLoading && products.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (!isLoading && products.isEmpty() && errorMessage != null) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp),
                    )
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
        products =
            InMemoryProductRepository.products.toList().take(4).mapIndexed { index, product ->
                ShoppingProductUiState(
                    product = product,
                    quantity = if (index % 2 == 0) 0 else 2,
                )
            },
        recentProducts = InMemoryProductRepository.products.take(4),
        showMoreButton = true,
        isLoading = false,
        errorMessage = null,
        onProductClick = {},
        onMoreClick = {},
        onAddToCart = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
