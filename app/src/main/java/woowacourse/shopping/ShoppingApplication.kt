package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.AppDatabase
import woowacourse.shopping.data.datasourceimpl.ProductLocalDataSource
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentlyViewedProductsRepositoryImpl

class ShoppingApplication : Application() {
    val appDatabase: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val shoppingItemsRepository: ProductRepositoryImpl by lazy {
        val productDao = appDatabase.productDao()
        val productLocalDataSource = ProductLocalDataSource.getInstance(productDao)
        ProductRepositoryImpl(productLocalDataSource)
    }
    val cartRepository: CartRepositoryImpl by lazy { CartRepositoryImpl.getInstance(appDatabase) }
    val recentlyViewedProductsRepository: RecentlyViewedProductsRepositoryImpl by lazy {
        RecentlyViewedProductsRepositoryImpl.getInstance(appDatabase)
    }
}
