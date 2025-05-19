package woowacourse.shopping

import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository

object ShoppingProvider {
    private var _shoppingCartRepository: ShoppingCartRepository? = null

    val shoppingCartRepository: ShoppingCartRepository get() = _shoppingCartRepository ?: throw IllegalArgumentException()

    private var _productRepository: ProductRepository? = null

    val productRepository: ProductRepository get() = _productRepository ?: throw IllegalArgumentException()

    fun initShoppingCartRepository(repository: ShoppingCartRepository) {
        _shoppingCartRepository = repository
    }

    fun initProductRepository(repository: ProductRepository) {
        _productRepository = repository
    }
}
