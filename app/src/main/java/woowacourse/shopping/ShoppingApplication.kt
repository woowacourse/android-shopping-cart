package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.data.dummyProducts

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initRepository()
    }

    private fun initRepository() {
        initProductRepository()
        initCartRepository()
    }

    private fun initProductRepository() {
        val productRepository = ProductRepositoryImpl(dummyProducts)
        RepositoryProvider.initProductRepository(productRepository)
    }

    private fun initCartRepository() {
        val cartDao = ShoppingDatabase.getDatabase(this).cartDao()
        val cartRepository = CartRepositoryImpl(RepositoryProvider.productRepository, cartDao)
        RepositoryProvider.initCartRepository(cartRepository)
    }
}
