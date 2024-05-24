package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.AppDatabase

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}
