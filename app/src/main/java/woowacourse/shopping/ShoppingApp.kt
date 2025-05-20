package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.LocalCartRepository
import woowacourse.shopping.data.repository.LocalHistoryRepository
import woowacourse.shopping.data.repository.LocalProductRepository
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingApp : Application() {
    val productRepository: ProductRepository by lazy { LocalProductRepository(database.productDao()) }
    val cartRepository: CartRepository by lazy { LocalCartRepository(database.cartDao()) }
    val historyRepository: HistoryRepository by lazy { LocalHistoryRepository(database.historyDao()) }
    private val database: ShoppingDatabase by lazy { ShoppingDatabase.getInstance(this) }
}
