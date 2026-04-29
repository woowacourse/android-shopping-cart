package woowacourse.shopping.feature.detail.bridge

import woowacourse.shopping.core.data.CartRepository
import woowacourse.shopping.core.data.ProductRepository
import woowacourse.shopping.core.uimodel.toUiModel

class DetailBridge(
    private val productRepository: ProductRepository = ProductRepository,
    private val cartRepository: CartRepository = CartRepository,
    private val id: String,
) {
    val product = productRepository.getProductById(id).toUiModel()

    fun addToCart(productId: String): Boolean {
        val product = productRepository.getProductById(productId)
        return cartRepository.addItem(product)
    }
}
