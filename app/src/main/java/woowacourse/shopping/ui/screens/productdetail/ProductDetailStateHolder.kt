package woowacourse.shopping.ui.screens.productdetail

import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ProductDetailStateHolder(
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
    private val cartRepository: CartRepository = CartRepositoryImpl(),
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
