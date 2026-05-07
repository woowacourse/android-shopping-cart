package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.data.localdb.ShoppingDB
import kotlin.jvm.java

class ShoppingApplication : Application() {
    val database: ShoppingDB by lazy {
        Room.databaseBuilder(
            applicationContext,
            ShoppingDB::class.java,
            "shopping.db",
        ).build()
    }
}