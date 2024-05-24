package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.api.ProductMockWebServer
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDatabase
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl

class ShoppingApplication : Application() {
    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(ProductMockWebServer())
    }

    override fun onCreate() {
        super.onCreate()
        RecentProductDatabase.initialize(this)
        CartDatabase.initialize(this)
        productRepository.start()
        forTest()
    }

    override fun onTerminate() {
        super.onTerminate()
        productRepository.shutdown()
    }

    private fun forTest() {
        Thread {
            RecentProductRepositoryImpl.get(RecentProductDatabase.database().recentProductDao())
                .deleteAll()

            CartRepositoryImpl.get(CartDatabase.database().cartDao())
                .deleteAll()
        }.start()
    }
}
