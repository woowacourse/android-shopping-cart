package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.di.AppContainer

class ShoppingApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
