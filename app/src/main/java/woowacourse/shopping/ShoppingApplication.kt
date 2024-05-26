package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.AppDatabase

class ShoppingApplication : Application() {
    val appDatabase: AppDatabase by lazy { AppDatabase.getInstance(this) }
}
