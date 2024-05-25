package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.db.cart.CartDatabase
import woowacourse.shopping.data.db.recent.RecentProductDatabase

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        cartDatabase = CartDatabase.getInstance(this)
        recentProductDatabase = RecentProductDatabase.getInstance(this)
    }

    companion object {
        lateinit var cartDatabase: CartDatabase
        lateinit var recentProductDatabase: RecentProductDatabase
    }
}
