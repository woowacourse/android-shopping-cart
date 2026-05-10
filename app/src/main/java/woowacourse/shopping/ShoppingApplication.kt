package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.data.local.AppDatabase
import woowacourse.shopping.data.remote.MockServer

class ShoppingApplication : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        MockServer.start()
        database = Room.databaseBuilder(
                context = this,
                klass = AppDatabase::class.java,
                name = "shopping-database"
            ).fallbackToDestructiveMigration(false)
            .build()
    }
}
