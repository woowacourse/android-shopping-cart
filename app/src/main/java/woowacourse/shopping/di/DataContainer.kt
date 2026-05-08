package woowacourse.shopping.di

import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.cart.MockCartRepository
import woowacourse.shopping.repository.product.MockProductRepository
import woowacourse.shopping.repository.product.ProductRepository

object DataContainer {
    val cartRepository: CartRepository by lazy { MockCartRepository() }
    val productRepository: ProductRepository by lazy { MockProductRepository() }
}
