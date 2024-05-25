package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.RoomCartRepository
import woowacourse.shopping.data.cart.database.CartDataBase
import woowacourse.shopping.data.product.MockWebServerProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.dummyProducts
import woowacourse.shopping.data.product.server.MockWebProductServer
import woowacourse.shopping.data.product.server.ProductMockWebServerDispatcher
import woowacourse.shopping.data.product.server.ProductServer

class ShoppingCartApplication : Application() {
    private val productServer: ProductServer by lazy { MockWebProductServer(ProductMockWebServerDispatcher(dummyProducts)) }

    override fun onCreate() {
        super.onCreate()
        productServer.start()
        ProductRepository.setInstance(MockWebServerProductRepository(productServer))
        CartRepository.setInstance(RoomCartRepository(CartDataBase.instance(this).cartDao()))
    }

    override fun onTerminate() {
        super.onTerminate()
        productServer.shutDown()
    }
}
