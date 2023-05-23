package woowacourse.shopping.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.util.extension.parseQueryString

class ShoppingMockWebServer : Thread() {
    private val mockWebServer: MockWebServer = MockWebServer()
    private lateinit var _baseUrl: String
    val baseUrl: String get() = _baseUrl

    override fun run() {
        super.run()
        mockWebServer.url("/")
        mockWebServer.dispatcher = getDispatcher()
        _baseUrl = "http://localhost:${mockWebServer.port}"
    }

    private fun getDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.method) {
                GET -> {
                    val path = request.path ?: return MockResponse().setResponseCode(404)
                    return processGet(path)
                }

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private fun processGet(path: String): MockResponse = when {
        path.startsWith("/products") && path.contains("productId") -> {
            val productId = path.parseQueryString()["productId"]?.toInt() ?: 1
            MockResponse()
                .setHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(getProductById(productId))
        }

        path.startsWith("/products") -> {
            val queryStrings = path.parseQueryString()
            val startId = queryStrings["start"]?.toInt() ?: 1
            val offset = queryStrings["count"]?.toInt() ?: 20
            MockResponse()
                .setHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(getProducts(startId, offset))
        }

        else -> MockResponse().setResponseCode(404)
    }
}
