package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.api.ProductMockWebServer
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDatabase

class ShoppingApplication : Application() {
    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(ProductMockWebServer())
    }

    override fun onCreate() {
        super.onCreate()
        RecentProductDatabase.initialize(this)
        CartDatabase.initialize(this)
        productRepository.start()
    }

    override fun onTerminate() {
        super.onTerminate()
        productRepository.shutdown()
    }
}
