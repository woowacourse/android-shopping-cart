package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRecentRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.source.ProductDataSourceImpl

class ShoppingApplication : Application() {
    val database by lazy { ShoppingDatabase.getDatabase(this) }

    val cartRepository by lazy {
        CartRepositoryImpl(database.cartDao())
    }

    val productRepository by lazy {
        ProductRepositoryImpl()
    }

    val productRecentRepository by lazy {
        ProductRecentRepositoryImpl(database.recentProductDao(), ProductDataSourceImpl)
    }
}
