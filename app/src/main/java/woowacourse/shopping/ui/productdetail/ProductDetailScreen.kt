package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.common.component.network.NetworkStatusBanner
import woowacourse.shopping.ui.common.component.recentlyviewed.LastViewedProductCard
import woowacourse.shopping.ui.productdetail.component.CartAddButton
import woowacourse.shopping.ui.productdetail.component.ProductDetailBody
import woowacourse.shopping.ui.productdetail.component.ProductDetailHeader

@Composable
fun ProductDetailScreen(
    product: Product,
    lastViewedProduct: Product?,
    quantity: Int,
    isAdding: Boolean,
    isNetworkConnected: Boolean,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onAddToCart: () -> Unit,
    onLastViewedProductClick: (Product) -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProductDetailHeader(onCloseClick = onCloseClick)

        if (!isNetworkConnected) {
            NetworkStatusBanner(modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp))
        }

        ProductDetailBody(
            product = product,
            quantity = quantity,
            onIncreaseQuantity = onIncreaseQuantity,
            onDecreaseQuantity = onDecreaseQuantity,
        )

        if (lastViewedProduct != null) {
            LastViewedProductCard(
                name = lastViewedProduct.name,
                modifier = Modifier.padding(horizontal = 18.dp),
                onClick = { onLastViewedProductClick(lastViewedProduct) },
            )
            Spacer(modifier = Modifier.size(16.dp))
        }

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
        lastViewedProduct = InMemoryProductRepository.BBOYAMI,
        quantity = 0,
        isAdding = false,
        isNetworkConnected = true,
        onCloseClick = {},
        onAddToCart = {},
        onLastViewedProductClick = {},
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
        lastViewedProduct = InMemoryProductRepository.BBOYAMI,
        quantity = 2,
        isAdding = false,
        isNetworkConnected = true,
        onCloseClick = {},
        onAddToCart = {},
        onLastViewedProductClick = {},
        onIncreaseQuantity = {},
        onDecreaseQuantity = {},
    )
}
