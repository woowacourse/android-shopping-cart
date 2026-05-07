package woowacourse.shopping

import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.cart.InMemoryCartRepository
import woowacourse.shopping.repository.product.InMemoryProductRepository
import woowacourse.shopping.repository.product.ProductRepository

object AppContainer {
    fun createProductRepository(packageName:String) : ProductRepository = InMemoryProductRepository(packageName)
    val cartRepository: CartRepository = InMemoryCartRepository()
}