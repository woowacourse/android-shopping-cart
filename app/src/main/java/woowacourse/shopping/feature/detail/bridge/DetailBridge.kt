package woowacourse.shopping.feature.detail.bridge

import woowacourse.shopping.core.repository.CartRepository
import woowacourse.shopping.core.repository.MockRepository
import woowacourse.shopping.core.repository.ProductRepository
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.core.uimodel.toUiModel

class DetailBridge(
    private val productRepository: ProductRepository = MockRepository(),
    private val cartRepository: CartRepository = CartRepository,
) {
    suspend fun getProduct(id: String): ProductUiModel = productRepository.getProductById(id).toUiModel()

    suspend fun addToCart(productId: String): Boolean {
        val product = productRepository.getProductById(productId)
        return cartRepository.addItem(product)
    }
}
