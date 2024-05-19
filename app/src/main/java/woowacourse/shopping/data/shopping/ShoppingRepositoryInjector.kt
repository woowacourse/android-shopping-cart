package woowacourse.shopping.data.shopping

import androidx.annotation.VisibleForTesting

object ShoppingRepositoryInjector {

    @Volatile
    private var instance: ShoppingRepository? = null

    fun shoppingRepository(): ShoppingRepository = instance ?: synchronized(this) {
        instance ?: DefaultShoppingRepository(
            DummyShoppingDataSource
        ).also { instance = it }
    }

    @VisibleForTesting
    fun setShoppingRepository(shoppingRepository: ShoppingRepository) {
        instance = shoppingRepository
    }
}