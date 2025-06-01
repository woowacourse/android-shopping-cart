package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.server.DevMockServer
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    lateinit var database: ShoppingDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        thread { DevMockServer.start() }
        database = ShoppingDatabase.getInstance(applicationContext)
    }
}
