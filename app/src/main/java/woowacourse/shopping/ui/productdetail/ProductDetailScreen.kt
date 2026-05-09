package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.component.ShoppingLoading
import woowacourse.shopping.ui.productdetail.component.CartAddButton
import woowacourse.shopping.ui.productdetail.component.ProductDetailBody
import woowacourse.shopping.ui.productdetail.component.ProductDetailHeader

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
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
                onDecreaseClick = { viewModel.decrease() }
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
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProductDetailHeader(onCloseClick = onCloseClick)

        ProductDetailBody(
            product = product,
            totalPrice = totalPrice,
            count = count,
            onIncreaseClick = onIncreaseClick,
            onDecreaseClick = onDecreaseClick
        )

        Spacer(modifier = Modifier.weight(1f))

        CartAddButton(onClick = onAddToCartClick)
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductDetailScreenPreview() {
    val product =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )

    ProductDetailScreen(
        product = product,
        onCloseClick = {},
        onAddToCartClick = {},
        totalPrice = 30000,
        count = 3,
        onIncreaseClick = {},
        onDecreaseClick = {},
    )
}
