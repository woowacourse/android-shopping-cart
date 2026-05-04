package woowacourse.shopping.feature.productDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.retain.retain
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.feature.productDetail.model.ProductInfo
import woowacourse.shopping.feature.productDetail.model.toUiModel

class ProductDetailStateHolder(
    productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    productId: String,
) {
    private val product: Product = productRepository.getProduct(productId)

    val productInfo: ProductInfo = product.toUiModel()

    fun addToCart() = cartRepository.addCartItem(CartItem(product, Quantity(1)))
}

@Composable
fun retainProductDetailStateHolder(productId: String): ProductDetailStateHolder =
    retain(productId) {
        ProductDetailStateHolder(ProductRepositoryImpl, CartRepositoryImpl, productId)
    }
