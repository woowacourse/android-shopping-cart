package woowacourse.shopping.di.provider

import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

object RepositoryProvider {
    lateinit var productRepository: ProductRepository
        private set

    lateinit var cartRepository: CartRepository
        private set

    lateinit var recentProductRepository: RecentProductRepository
        private set

    fun initProductRepository(repository: ProductRepository) {
        productRepository = repository
    }

    fun initCartRepository(repository: CartRepository) {
        cartRepository = repository
    }

    fun initRecentProductRepository(repository: RecentProductRepository) {
        recentProductRepository = repository
    }
}
