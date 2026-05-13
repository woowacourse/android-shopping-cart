package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.source.remote.mock.MockServerManager

class ShoppingApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        MockServerManager.start()

        appContainer = AppContainer(context = applicationContext)
    }
}
