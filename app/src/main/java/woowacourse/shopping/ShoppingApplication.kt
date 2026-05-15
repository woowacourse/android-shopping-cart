package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.source.remote.ShoppingMockServer

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ShoppingMockServer.start()
    }
}
