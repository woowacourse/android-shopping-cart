package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartDatabase

class ShoppingApplication : Application() {
    lateinit var database: CartDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = CartDatabase.getInstance(instance)
    }

    companion object {
        private lateinit var instance: ShoppingApplication

        fun getInstance(): ShoppingApplication = instance
    }
}
