package woowacourse.shopping

import android.app.Application
import android.util.Log
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.remote.ProductMockWebServerDispatcher
import woowacourse.shopping.di.DataSourceModule
import woowacourse.shopping.di.DatabaseModule
import java.io.IOException

class ShoppingApplication : Application() {
    private val mockWebServer = MockWebServer()

    override fun onCreate() {
        super.onCreate()
        startMockServer()
        DatabaseModule.init(this)
        DataSourceModule.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mockWebServer.shutdown()
    }

    private fun startMockServer() {
        Thread {
            try {
                mockWebServer.dispatcher = ProductMockWebServerDispatcher
                mockWebServer.start(PORT_NUMBER)
                Log.d("ShoppingApplication", "MockWebServer started on port $PORT_NUMBER")
            } catch (e: IOException) {
                Log.e("ShoppingApplication", "Failed to start MockWebServer", e)
            }
        }.start()
    }

    companion object {
        private const val PORT_NUMBER = 1999
    }
}
