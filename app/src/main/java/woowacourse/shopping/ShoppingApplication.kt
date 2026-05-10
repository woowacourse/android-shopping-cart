package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.di.AppContainer

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(this)
    }
}
