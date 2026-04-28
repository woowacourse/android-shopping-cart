package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.productdetail.component.CartAddButton
import woowacourse.shopping.ui.productdetail.component.ProductDetailBody
import woowacourse.shopping.ui.productdetail.component.ProductDetailHeader

@Composable
fun ProductDetailScreen(
    product: Product,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCart: (Product, Int) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProductDetailHeader(onCloseClick = onCloseClick)

        ProductDetailBody(product = product)

        Spacer(modifier = Modifier.weight(1f))

        CartAddButton(onClick = { onAddToCart(product, 1) })
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductDetailScreenPreview() {
    val product = InMemoryProductRepository.APPLE
    ProductDetailScreen(
        product = product,
        onCloseClick = {},
        onAddToCart = { _, _ -> }
    )
}
