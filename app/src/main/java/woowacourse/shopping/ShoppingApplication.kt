package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.data.dummyProducts

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initRepository()
    }

    private fun initRepository() {
        val cartDao = ShoppingDatabase.getDatabase(this).cartDao()

        initCartRepository(cartDao)
        initProductRepository(cartDao)
    }

    private fun initProductRepository(cartDao: CartDao) {
        val productRepository = ProductRepositoryImpl(dummyProducts, cartDao)
        RepositoryProvider.initProductRepository(productRepository)
    }

    private fun initCartRepository(cartDao: CartDao) {
        val cartRepository = CartRepositoryImpl(cartDao)
        RepositoryProvider.initCartRepository(cartRepository)
    }
}
