package woowacourse.shopping.data

import woowacourse.shopping.data.cart.CartRepositoryMockImpl
import woowacourse.shopping.data.product.ProductRepositoryMockImpl

object DataProvider {
    val productRepository = ProductRepositoryMockImpl()
    val cartRepository = CartRepositoryMockImpl()
}
