package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.data.remote.mock.MockWebServerProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val baseUrl = MockWebServerProvider.start()
        AppContainer.initialize(this, baseUrl)
    }

    override fun onTerminate() {
        super.onTerminate()
        MockWebServerProvider.shutdown()
    }
}
