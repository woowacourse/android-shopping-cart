package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase

class ShoppingApplication : Application() {
    lateinit var database: ShoppingDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = ShoppingDatabase.getInstance(applicationContext)
    }
}
