package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.RecentProductDatabase

class ShoppingApplication : Application() {
    lateinit var cartDatabase: CartDatabase
        private set

    lateinit var recentProductDatabase: RecentProductDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        cartDatabase = CartDatabase.getInstance(instance)
        recentProductDatabase = RecentProductDatabase.getInstance(instance)
    }

    companion object {
        private lateinit var instance: ShoppingApplication

        fun getInstance(): ShoppingApplication = instance
    }
}
