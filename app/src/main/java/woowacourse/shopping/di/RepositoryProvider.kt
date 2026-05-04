package woowacourse.shopping.di

import woowacourse.shopping.data.InMemoryProductRepository
import woowacourse.shopping.domain.repository.ProductRepository

object RepositoryProvider {
    val productRepository: ProductRepository = InMemoryProductRepository()
}
