package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.AppDatabase
import woowacourse.shopping.data.repository.DummyShoppingItemsRepository
import woowacourse.shopping.data.repository.InMemoryCartRepository
import woowacourse.shopping.data.repository.InMemoryRecentlyViewedProductsRepository

class ShoppingApplication : Application() {
    val appDatabase: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val shoppingItemsRepository: DummyShoppingItemsRepository by lazy { DummyShoppingItemsRepository.getInstance(appDatabase) }
    val cartRepository: InMemoryCartRepository by lazy { InMemoryCartRepository.getInstance(appDatabase) }
    val recentlyViewedProductsRepository: InMemoryRecentlyViewedProductsRepository by lazy {
        InMemoryRecentlyViewedProductsRepository.getInstance(appDatabase)
    }
}
