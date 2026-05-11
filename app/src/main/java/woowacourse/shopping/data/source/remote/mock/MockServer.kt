package woowacourse.shopping.data.source.remote.mock

import mockwebserver3.MockWebServer

object MockServer {
    private val server =
        MockWebServer().apply {
            dispatcher = MockDispatcher()
        }

    private var started = false

    @Synchronized
    fun start() {
        if (started) return
        server.start()
        started = true
    }

    val baseUrl: String
        get() = server.url("/").toString()
}
