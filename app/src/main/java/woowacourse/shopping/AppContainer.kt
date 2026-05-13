package woowacourse.shopping

import android.content.Context
import woowacourse.shopping.data.local.AppDatabase
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.dao.RecentProductDao
import woowacourse.shopping.data.remote.source.ProductRemoteDataSource
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class AppContainer(private val context: Context) {
    private val database: AppDatabase by lazy { AppDatabase.getDatabase(context) }

    private val cartDao: CartDao by lazy { database.cartDao() }
    private val recentProductDao: RecentProductDao by lazy { database.recentProductDao() }

    private val productDataSource: ProductRemoteDataSource by lazy { ProductRemoteDataSource() }

    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(productDataSource)
    }

    val cartRepository: CartRepository by lazy {
        CartRepositoryImpl(cartDao)
    }

    val recentProductRepository: RecentProductRepository by lazy {
        RecentProductRepositoryImpl(recentProductDao)
    }
}
