package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.common.component.ShoppingLoading
import woowacourse.shopping.ui.productdetail.component.CartAddButton
import woowacourse.shopping.ui.productdetail.component.ProductDetailBody
import woowacourse.shopping.ui.productdetail.component.ProductDetailHeader

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onLastViewedProductClick: (Product) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        uiState.product?.let { product ->
            ProductDetailScreen(
                product = product,
                totalPrice = uiState.totalPrice.value,
                count = uiState.selectedQuantity,
                onCloseClick = onCloseClick,
                onAddToCartClick = {
                    viewModel.addToCart()
                    onAddToCartClick()
                },
                onIncreaseClick = { viewModel.increase() },
                onDecreaseClick = { viewModel.decrease() },
                lastViewedProduct = uiState.lastViewedProduct,
                onLastViewedProductClick = onLastViewedProductClick,
            )
        }

        if (uiState.isLoading) ShoppingLoading()
    }
}

@Composable
fun ProductDetailScreen(
    product: Product,
    totalPrice: Int,
    count: Int,
    lastViewedProduct: Product?,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    onLastViewedProductClick: (Product) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ProductDetailHeader(onCloseClick = onCloseClick)
        },
        bottomBar = {
            CartAddButton(onClick = onAddToCartClick)
        },
    ) { paddingValues ->
        ProductDetailBody(
            product = product,
            totalPrice = totalPrice,
            count = count,
            onIncreaseClick = onIncreaseClick,
            onDecreaseClick = onDecreaseClick,
            lastViewedProduct = lastViewedProduct,
            onLastViewedProductClick = onLastViewedProductClick,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductDetailScreenPreview() {
    val product1 =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )

    val product2 =
        Product(
            name = "[든든] 동원 스위트콘",
            price = Money(1000),
            imageUrl = "",
        )

    ProductDetailScreen(
        product = product1,
        onCloseClick = {},
        onAddToCartClick = {},
        totalPrice = 30000,
        count = 3,
        onIncreaseClick = {},
        onDecreaseClick = {},
        lastViewedProduct = product2,
        onLastViewedProductClick = {},
    )
}
