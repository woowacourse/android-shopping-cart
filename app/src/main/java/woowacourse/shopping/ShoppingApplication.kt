package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.app.AppContainer

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.initialize(this)
    }
}
