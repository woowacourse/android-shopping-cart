package woowacourse.shopping.ui.screens.productdetail

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ProductDetailStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    targetProductId: String,
) {
    val product: Product = productRepository.getProductById(targetProductId)

    fun addToCart(
        productId: String,
        amount: Int = 1,
    ) {
        val targetProduct = productRepository.getProductById(productId)

        cartRepository.addItem(targetProduct, amount)
    }
}
