package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.db.AppDatabase

class ShoppingApplication: Application() {

    override fun onCreate() {
        AppDatabase.getDatabase(this)
        super.onCreate()
    }
}