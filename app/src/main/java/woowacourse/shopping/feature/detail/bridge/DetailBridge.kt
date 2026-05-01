package woowacourse.shopping.feature.detail.bridge

import woowacourse.shopping.core.repository.CartRepository
import woowacourse.shopping.core.repository.InMemoryProductRepository
import woowacourse.shopping.core.repository.ProductRepository
import woowacourse.shopping.core.uimodel.ProductUiModel
import woowacourse.shopping.core.uimodel.toUiModel
import woowacourse.shopping.feature.cart.model.AddItemResult

class DetailBridge(
    private val productRepository: ProductRepository = InMemoryProductRepository(),
    private val cartRepository: CartRepository = CartRepository,
) {
    suspend fun getProduct(id: String): ProductUiModel = productRepository.getProductById(id).toUiModel()

    suspend fun addToCart(productId: String): AddItemResult {
        val product = productRepository.getProductById(productId)
        return cartRepository.addItem(product)
    }
}
