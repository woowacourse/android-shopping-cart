package woowacourse.shopping.ui.shopping

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.common.component.network.NetworkStatusBanner
import woowacourse.shopping.ui.shopping.component.ShoppingBody
import woowacourse.shopping.ui.shopping.component.ShoppingHeader

@Composable
fun ShoppingScreen(
    products: List<ShoppingProductUiState>,
    recentProducts: List<Product>,
    cartQuantity: Int,
    hasNext: Boolean,
    isLoading: Boolean,
    isNetworkConnected: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
    onAddToCart: (ProductId) -> Unit,
    onIncreaseQuantity: (ProductId) -> Unit,
    onDecreaseQuantity: (ProductId) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShoppingHeader(
            cartQuantity = cartQuantity,
            onCartClick = onCartClick,
        )

        if (!isNetworkConnected) {
            NetworkStatusBanner(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
        }

        ShoppingBody(
            products = products,
            recentProducts = recentProducts,
            showMoreButton = hasNext,
            isLoading = isLoading,
            errorMessage = errorMessage,
            modifier =
                Modifier
                    .padding(20.dp)
                    .weight(1f),
            onProductClick = onProductClick,
            onMoreClick = onMoreClick,
            onAddToCart = onAddToCart,
            onIncreaseQuantity = onIncreaseQuantity,
            onDecreaseQuantity = onDecreaseQuantity,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        products =
            InMemoryProductRepository.products.toList().take(6).mapIndexed { index, product ->
                ShoppingProductUiState(
                    product = product,
                    quantity = if (index < 2) 0 else 1,
                )
            },
        recentProducts = InMemoryProductRepository.products.take(4),
        cartQuantity = 4,
        hasNext = true,
        isLoading = false,
        isNetworkConnected = true,
        errorMessage = null,
        onCartClick = {},
        onProductClick = {},
        onMoreClick = {},
        onAddToCart = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
