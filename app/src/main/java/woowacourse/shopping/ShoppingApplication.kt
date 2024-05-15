package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartDatabase

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        database = CartDatabase.getInstance(applicationContext)
    }

    companion object {
        lateinit var database: CartDatabase
    }
}
