package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.RoomCartRepository
import woowacourse.shopping.data.cart.database.CartDataBase
import woowacourse.shopping.data.product.MockWebServerProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.dummyProducts
import woowacourse.shopping.data.product.server.MockWebProductServer
import woowacourse.shopping.data.product.server.MockWebProductServerDispatcher
import woowacourse.shopping.data.product.server.ProductServer
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.recent.RoomRecentProductRepository
import woowacourse.shopping.data.recent.database.RecentProductDataBase

class ShoppingCartApplication : Application() {
    private val productServer: ProductServer by lazy { MockWebProductServer(MockWebProductServerDispatcher(dummyProducts)) }

    override fun onCreate() {
        super.onCreate()
        productServer.start()
        ProductRepository.setInstance(MockWebServerProductRepository(productServer))
        RecentProductRepository.setInstance(RoomRecentProductRepository(RecentProductDataBase.instance(this).recentProductDao()))
        CartRepository.setInstance(RoomCartRepository(CartDataBase.instance(this).cartDao()))
    }

    override fun onTerminate() {
        super.onTerminate()
        productServer.shutDown()
    }
}
