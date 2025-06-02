package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.DatabaseProvider
import woowacourse.shopping.data.local.ShoppingCartDatabase
import woowacourse.shopping.data.local.dummy.ProductDummy
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.data.local.repository.HistoryRepository
import woowacourse.shopping.data.local.repository.ProductRepository

class ShoppingCartApplication : Application() {
    private lateinit var database : ShoppingCartDatabase
    private lateinit var productDummy : ProductDummy

    lateinit var cartRepository : CartRepository
    lateinit var  productRepository : ProductRepository
    lateinit var  historyRepository : HistoryRepository

    override fun onCreate() {
        super.onCreate()
        database = DatabaseProvider.provideDatabase(this)
        productDummy = ProductDummy

        cartRepository = CartRepository(database.cartItemDao(), productDummy)
        productRepository = ProductRepository(productDummy)
        historyRepository = HistoryRepository(database.historyDao())
    }
}
