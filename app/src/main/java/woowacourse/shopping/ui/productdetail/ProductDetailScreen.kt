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
    quantity: Int,
    isAdding: Boolean,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCart: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProductDetailHeader(onCloseClick = onCloseClick)

        ProductDetailBody(
            product = product,
            quantity = quantity,
            onIncreaseQuantity = onIncreaseQuantity,
            onDecreaseQuantity = onDecreaseQuantity,
        )

        Spacer(modifier = Modifier.weight(1f))
        CartAddButton(
            isEnabled = !isAdding,
            onClick = onAddToCart,
        )
    }
}

@Composable
@Preview(showBackground = true, name = "장바구니 담기")
private fun ProductDetailScreenAddToCartPreview() {
    val product = InMemoryProductRepository.APPLE
    ProductDetailScreen(
        product = product,
        quantity = 0,
        isAdding = false,
        onCloseClick = {},
        onAddToCart = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}

@Composable
@Preview(showBackground = true, name = "수량 스테퍼")
private fun ProductDetailScreenQuantityPreview() {
    val product = InMemoryProductRepository.APPLE
    ProductDetailScreen(
        product = product,
        quantity = 2,
        isAdding = false,
        onCloseClick = {},
        onAddToCart = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
