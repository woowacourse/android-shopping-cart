package woowacourse.shopping

import android.app.Application

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RepositoryProvider.init(this)
    }
}
