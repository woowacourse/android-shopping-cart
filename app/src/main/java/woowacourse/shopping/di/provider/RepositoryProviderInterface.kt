package woowacourse.shopping.di.provider

import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

interface RepositoryProviderInterface {
    fun initProductRepository(repository: ProductRepository)

    fun initCartRepository(repository: CartRepository)

    fun initRecentProductRepository(repository: RecentProductRepository)
}
