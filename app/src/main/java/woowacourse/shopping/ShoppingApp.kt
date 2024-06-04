package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.local.cart.ShoppingCartDao
import woowacourse.shopping.local.cart.ShoppingCartDatabase
import woowacourse.shopping.local.history.HistoryProductDao
import woowacourse.shopping.local.history.HistoryProductDatabase
import woowacourse.shopping.local.source.LocalHistoryProductDataSource
import woowacourse.shopping.local.source.LocalShoppingCartProductIdDataSource
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService
import woowacourse.shopping.remote.source.RemoteProductDataSource

class ShoppingApp : Application() {
    private val productsApi: ProductApiService by lazy { MockProductApiService() }

    private val shoppingCartDb: ShoppingCartDatabase by lazy { ShoppingCartDatabase.database(context = this) }
    private val shoppingCartDao: ShoppingCartDao by lazy { shoppingCartDb.dao() }

    private val historyProductDb: HistoryProductDatabase by lazy { HistoryProductDatabase.database(context = this) }
    private val historyProductDao: HistoryProductDao by lazy { historyProductDb.dao() }

    override fun onCreate() {
        super.onCreate()
        productSource = RemoteProductDataSource(productsApi)
        cartSource = LocalShoppingCartProductIdDataSource(shoppingCartDao)
        historySource = LocalHistoryProductDataSource(historyProductDao)
    }

    override fun onTerminate() {
        super.onTerminate()
        shoppingCartDb.close()
        historyProductDb.close()
    }

    companion object {
        lateinit var productSource: ProductDataSource
            private set

        lateinit var cartSource: ShoppingCartProductIdDataSource
            private set

        lateinit var historySource: ProductHistoryDataSource
            private set
    }
}
