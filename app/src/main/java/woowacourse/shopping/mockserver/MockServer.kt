package woowacourse.shopping.mockserver

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import kotlin.concurrent.thread

class MockServer {
    private lateinit var server: MockWebServer

    init {
        startMockServer()
    }

    fun createUrlWithBase(requestUrl: String): HttpUrl = server.url(requestUrl)

    private fun startMockServer() {
        thread {
            server = MockWebServer()
            server.dispatcher = MockProductDispatcher(dummyProducts)
        }
    }
}
