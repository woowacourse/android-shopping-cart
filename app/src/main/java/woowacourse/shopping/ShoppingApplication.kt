package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDatabase
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        RecentProductDatabase.initialize(this)
        CartDatabase.initialize(this)
        Thread {
            RecentProductRepositoryImpl.get(RecentProductDatabase.database().recentProductDao())
                .deleteAll()

            CartRepositoryImpl.get(CartDatabase.database().cartDao())
                .deleteAll()
        }.start()
    }
}
