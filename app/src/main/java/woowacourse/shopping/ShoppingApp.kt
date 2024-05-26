package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDataBase
import woowacourse.shopping.data.datasource.impl.MockServerProductDataSource
import woowacourse.shopping.data.datasource.impl.RoomCartItemDataSource
import woowacourse.shopping.data.datasource.impl.RoomRecentProductDataSource
import woowacourse.shopping.data.service.ProductDispatcher
import woowacourse.shopping.data.service.ProductMockServer
import woowacourse.shopping.repository.DefaultProductRepository
import woowacourse.shopping.repository.DefaultShoppingRepository
import kotlin.concurrent.thread

class ShoppingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initData()
    }

    private fun initData() {
        thread {
            val dataBase = ShoppingDataBase.database(this)
            val mockServer = ProductMockServer.instance(ProductDispatcher())
            val productDataSource = MockServerProductDataSource(mockServer)
            DefaultShoppingRepository.initialize(
                RoomCartItemDataSource(dataBase.shoppingCartItemDao()),
                productDataSource,
            )
            DefaultProductRepository.initialize(
                RoomRecentProductDataSource(dataBase.recentProductDao()),
                productDataSource,
            )
        }.join()
    }

    override fun onTerminate() {
        super.onTerminate()
        ProductMockServer.shutDown()
    }
}
