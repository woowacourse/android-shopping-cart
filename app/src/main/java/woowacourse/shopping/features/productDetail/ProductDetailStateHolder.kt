package woowacourse.shopping.features.productDetail

import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductDetailStateHolder(
    productId: String,
    productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) {
    val detailProduct = productRepository.getProduct(productId)

    fun addToCart() {
        cartRepository.addCartItem(CartItem(product = detailProduct))
    }
}
