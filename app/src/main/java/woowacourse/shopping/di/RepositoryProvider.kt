package woowacourse.shopping.di

import woowacourse.shopping.domain.repository.ShoppingRepository

object RepositoryProvider {
    lateinit var shoppingRepository: ShoppingRepository
        private set

    fun initShoppingRepository(repository: ShoppingRepository) {
        shoppingRepository = repository
    }
}
