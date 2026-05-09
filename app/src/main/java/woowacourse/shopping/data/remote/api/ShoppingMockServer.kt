package woowacourse.shopping.data.remote.api

import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import woowacourse.shopping.constants.MockData

object ShoppingMockServer {
    private var mockWebServer: MockWebServer? = null

    const val PORT = 12345
    const val BASE_URL = "http://localhost:$PORT/"

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.target
            return when {
                path == "/products" -> {
                    MockResponse.Builder()
                        .code(200)
                        .body(MockData.MOCK_PRODUCTS_LIST)
                        .addHeader("Content-Type", "application/json")
                        .build()
                }
                path.startsWith("/products/") -> {
                    val id = path.substringAfterLast("/")
                    val json = MockData.getProductJson(id)
                    MockResponse.Builder()
                        .code(200)
                        .body(json)
                        .addHeader("Content-Type", "application/json")
                        .build()
                }
                else -> MockResponse.Builder().code(404).build()
            }
        }
    }

    fun start() {
        if (mockWebServer != null) return

        val server = MockWebServer()
        mockWebServer = server
        server.dispatcher = dispatcher

        Thread {
            try {
                server.start(PORT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun shutdown() {
        mockWebServer?.close()
        mockWebServer = null
    }
}
