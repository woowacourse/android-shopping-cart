package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl

class ShoppingApplication : Application() {
    val database by lazy { ShoppingDatabase.getDatabase(this) }

    val cartRepository by lazy {
        CartRepositoryImpl(database.cartDao())
    }

    val productRepository by lazy {
        ProductRepositoryImpl()
    }
}
