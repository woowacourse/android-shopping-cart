package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.productdetail.component.CartAddButton
import woowacourse.shopping.ui.productdetail.component.ProductDetailBody
import woowacourse.shopping.ui.productdetail.component.ProductDetailHeader

@Composable
fun ProductDetailScreen(
    productToShow: Product,
    cartRepo: CartRepository,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCartClick: () -> Unit,
) {
    ProductDetailScreen(
        product = productToShow,
        modifier = modifier,
        onCloseClick = onCloseClick,
        onAddToCartClick = {
            cartRepo.add(productToShow)
            onAddToCartClick()
        }
    )
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
    val product = InMemoryProductRepository.APPLE
    ProductDetailScreen(
        product = product,
        onCloseClick = {},
        onAddToCartClick = { },
    )
}
