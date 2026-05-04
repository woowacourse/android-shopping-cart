package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun addToCart(productId: Uuid) {
        val product = productRepository.findProductById(productId) ?: return
        cartRepository.addProduct(product)
    }
}
