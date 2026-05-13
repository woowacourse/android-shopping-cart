package woowacourse.shopping

import android.app.Application

class ShoppingApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
