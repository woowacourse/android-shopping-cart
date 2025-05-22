package woowacourse.shopping.di

import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.ShoppingRepository

object RepositoryProvider {
    lateinit var shoppingRepository: ShoppingRepository
        private set

    lateinit var recentProductRepository: RecentProductRepository
        private set

    fun initShoppingRepository(repository: ShoppingRepository) {
        shoppingRepository = repository
    }

    fun initRecentProductRepository(repository: RecentProductRepository) {
        recentProductRepository = repository
    }
}
