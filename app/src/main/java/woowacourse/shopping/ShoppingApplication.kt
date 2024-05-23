package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.db.cart.CartDatabase

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        database = CartDatabase.getInstance(this)
    }

    companion object {
        lateinit var database: CartDatabase
    }
}
