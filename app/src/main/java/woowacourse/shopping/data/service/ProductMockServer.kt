package woowacourse.shopping.data.service

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer

class ProductMockServer {
    companion object {
        @Volatile
        private var instance: MockWebServer? = null

        fun instance(dispatcher: Dispatcher): MockWebServer =
            instance ?: synchronized(this) {
                val newInstance = MockWebServer().apply { this.dispatcher = dispatcher }
                instance = newInstance
                return newInstance
            }

        fun shutDown() {
            instance?.shutdown()
        }
    }
}
