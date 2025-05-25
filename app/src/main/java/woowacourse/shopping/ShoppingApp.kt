package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.di.NetworkModule
import woowacourse.shopping.data.di.NetworkModule.productApi

class ShoppingApp : Application() {
    private val database: ShoppingDatabase by lazy { ShoppingDatabase.getInstance(this) }
    val productRepository: woowacourse.shopping.domain.repository.ProductRepository by lazy {
        woowacourse.shopping.data.repository
            .ProductRepository(database.productDao(), productApi)
    }
    val cartRepository: woowacourse.shopping.domain.repository.CartRepository by lazy {
        woowacourse.shopping.data.repository
            .CartRepository(database.cartDao())
    }
    val historyRepository: woowacourse.shopping.domain.repository.HistoryRepository by lazy {
        woowacourse.shopping.data.repository
            .HistoryRepository(database.historyDao())
    }

    override fun onCreate() {
        super.onCreate()
        NetworkModule.init(database.productDao())
    }
}
