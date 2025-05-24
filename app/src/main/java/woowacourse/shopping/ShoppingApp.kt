package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.di.NetworkModule
import woowacourse.shopping.data.di.NetworkModule.productService
import woowacourse.shopping.data.repository.ComplexProductRepository
import woowacourse.shopping.data.repository.LocalCartRepository
import woowacourse.shopping.data.repository.LocalHistoryRepository
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingApp : Application() {
    private val database: ShoppingDatabase by lazy { ShoppingDatabase.getInstance(this) }
    val productRepository: ProductRepository by lazy { ComplexProductRepository(database.productDao(), productService) }
    val cartRepository: CartRepository by lazy { LocalCartRepository(database.cartDao()) }
    val historyRepository: HistoryRepository by lazy { LocalHistoryRepository(database.historyDao()) }

    override fun onCreate() {
        super.onCreate()
        NetworkModule.init(database.productDao())
    }
}
