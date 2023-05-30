package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.product.ProductMockWebServer

class App : Application() {
    override fun onTerminate() {
        super.onTerminate()
        ProductMockWebServer.shutDownServer()
    }
}
