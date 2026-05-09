package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.di.DataContainer

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataContainer.init(this)
    }
}
