package woowacourse.shopping.di.provider

import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

object RepositoryProvider : RepositoryProviderInterface {
    lateinit var productRepository: ProductRepository
        private set

    lateinit var cartRepository: CartRepository
        private set

    lateinit var recentProductRepository: RecentProductRepository
        private set

    override fun initProductRepository(repository: ProductRepository) {
        productRepository = repository
    }

    override fun initCartRepository(repository: CartRepository) {
        cartRepository = repository
    }

    override fun initRecentProductRepository(repository: RecentProductRepository) {
        recentProductRepository = repository
    }
}
