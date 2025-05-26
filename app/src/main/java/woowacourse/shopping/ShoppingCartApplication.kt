package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl

class ShoppingCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CartRepositoryImpl.initialize(this, ProductRepositoryImpl())
    }
}
