package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.DatabaseProvider
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.data.local.repository.HistoryRepository
import woowacourse.shopping.data.local.repository.ProductRepository

class ShoppingCartApplication : Application() {
    private val database = DatabaseProvider.provideDatabase(this)

    val cartRepository : CartRepository by lazy { CartRepository(database.cartItemDao(), database.cartDao()) }
    val productRepository : ProductRepository by lazy { ProductRepository(database.cartDao()) }
    val historyRepository : HistoryRepository by lazy { HistoryRepository(database.historyDao()) }
}
