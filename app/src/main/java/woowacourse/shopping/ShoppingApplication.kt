package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.PRODUCT_DATA
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductHistoryRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingDatabase.getDatabase(this) }
    val productRepository by lazy { ProductRepositoryImpl(database.productDao()) }
    val cartRepository by lazy { CartRepositoryImpl(database.cartDao()) }
    val productHistoryRepository by lazy { ProductHistoryRepositoryImpl(database.productHistoryDao()) }
}
