package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase

class ShoppingApplication : Application() {
    lateinit var shoppingDatabase: ShoppingDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        shoppingDatabase = ShoppingDatabase.getInstance(instance)
    }

    companion object {
        private lateinit var instance: ShoppingApplication

        fun getInstance(): ShoppingApplication = instance
    }
}
