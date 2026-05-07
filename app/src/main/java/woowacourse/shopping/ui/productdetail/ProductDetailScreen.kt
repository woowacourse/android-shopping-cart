package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.component.ShoppingLoading
import woowacourse.shopping.ui.productdetail.component.CartAddButton
import woowacourse.shopping.ui.productdetail.component.ProductDetailBody
import woowacourse.shopping.ui.productdetail.component.ProductDetailHeader
import java.util.UUID

@Composable
fun ProductDetailScreen(
    productId: UUID,
    state: ProductDetailScreenState,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
) {
    LaunchedEffect(productId) {
        state.findProduct(productId)
    }

    if (state.isLoading) {
        ShoppingLoading()
    } else {
        state.productToShow?.let { product ->
            ProductDetailScreen(
                product = product,
                modifier = modifier,
                onCloseClick = onCloseClick,
                onAddToCartClick = {
                    state.addToCart(product)
                    onAddToCartClick()
                },
            )
        }
    }
}

@Composable
fun ProductDetailScreen(
    product: Product,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProductDetailHeader(onCloseClick = onCloseClick)

        ProductDetailBody(product = product)

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
        onAddToCartClick = { },
    )
}
