package woowacourse.shopping.di

import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

object AppContainer {
    val productRepository: ProductRepository = InMemoryProductRepository()
    val cartRepository: CartRepository = InMemoryCartRepository()
}
