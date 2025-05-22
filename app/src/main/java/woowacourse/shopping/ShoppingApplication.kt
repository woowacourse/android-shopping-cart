package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.RecentProductDao
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.data.dummyProducts

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initRepository()
    }

    private fun initRepository() {
        val cartDao = ShoppingDatabase.getDatabase(this).cartDao()
        val recentProductDao = ShoppingDatabase.getDatabase(this).recentProductDao()

        initCartRepository(cartDao)
        initProductRepository(cartDao, recentProductDao)
    }

    private fun initProductRepository(
        cartDao: CartDao,
        recentProductDao: RecentProductDao,
    ) {
        val productRepository = ProductRepositoryImpl(dummyProducts, cartDao, recentProductDao)
        RepositoryProvider.initProductRepository(productRepository)
    }

    private fun initCartRepository(cartDao: CartDao) {
        val cartRepository = CartRepositoryImpl(cartDao)
        RepositoryProvider.initCartRepository(cartRepository)
    }
}
