package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.AppDatabase
import woowacourse.shopping.data.datasourceimpl.ProductLocalDataSource
import woowacourse.shopping.data.repository.InMemoryCartRepository
import woowacourse.shopping.data.repository.InMemoryRecentlyViewedProductsRepository
import woowacourse.shopping.data.repository.ProductRepositoryImpl

class ShoppingApplication : Application() {
    val appDatabase: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val shoppingItemsRepository: ProductRepositoryImpl by lazy {
        val productDao = appDatabase.productDao()
        val productLocalDataSource = ProductLocalDataSource.getInstance(productDao)
        ProductRepositoryImpl(productLocalDataSource)
    }
    val cartRepository: InMemoryCartRepository by lazy { InMemoryCartRepository.getInstance(appDatabase) }
    val recentlyViewedProductsRepository: InMemoryRecentlyViewedProductsRepository by lazy {
        InMemoryRecentlyViewedProductsRepository.getInstance(appDatabase)
    }
}
