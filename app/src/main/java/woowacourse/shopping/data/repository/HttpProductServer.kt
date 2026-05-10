package woowacourse.shopping.data.repository

import okhttp3.mockwebserver.MockWebServer

object HttpProductServer {
    private val server: MockWebServer by lazy {
        val mockWebServer = MockWebServer().apply {
            dispatcher = ProductMockDispatcher()
        }

        Thread {
            mockWebServer.start(12345)
        }.apply {
            start()
            join()
        }

        mockWebServer
    }

    val baseUrl: String
        get() = server.url("/").toString()
}
