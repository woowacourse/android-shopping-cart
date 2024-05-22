package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.db.cart.CartDatabase
import woowacourse.shopping.util.SharedPrefs

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        database = CartDatabase.getInstance(this)
        sharedPrefs = SharedPrefs(this)
    }

    companion object {
        lateinit var database: CartDatabase
        lateinit var sharedPrefs: SharedPrefs
    }
}
