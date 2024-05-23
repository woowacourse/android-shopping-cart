package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.model.db.recentproduct.RecentProductDatabase
import woowacourse.shopping.model.db.recentproduct.RecentProductRepositoryImpl

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        RecentProductDatabase.initialize(this)
        Thread {
            RecentProductRepositoryImpl.get(RecentProductDatabase.database().recentProductDao())
                .deleteAll()
        }.start()
    }
}
