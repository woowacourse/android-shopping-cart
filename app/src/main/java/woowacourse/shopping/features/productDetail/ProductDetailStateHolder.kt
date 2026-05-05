package woowacourse.shopping.features.productDetail

import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductDetailStateHolder(
    private val productId: String,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) {
    fun detailProduct(): Product = productRepository.getProduct(productId)

    fun addToCart() {
        cartRepository.addCartItem(CartItem(product = detailProduct()))
    }
}
