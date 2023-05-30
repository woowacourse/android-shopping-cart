package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.respository.product.Mock

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread {
            Mock.startMockWebServer()
        }.start()
    }
}
