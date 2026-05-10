package woowacourse.shopping.data.remote.mock

import mockwebserver3.MockWebServer

object MockWebServer {
    private val server =
        MockWebServer().apply {
            dispatcher = MockServerDispatcher()
        }

    val baseUrl: String get() = server.url("/").toString()

    fun start() {
        server.start()
    }

    fun stop() {
        server.close()
    }
}
