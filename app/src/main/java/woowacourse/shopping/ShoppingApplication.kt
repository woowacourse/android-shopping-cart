package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.model.db.cart.CartDatabase
import woowacourse.shopping.model.db.cart.CartRepositoryImpl
import woowacourse.shopping.model.db.recentproduct.RecentProductDatabase
import woowacourse.shopping.model.db.recentproduct.RecentProductRepositoryImpl

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
