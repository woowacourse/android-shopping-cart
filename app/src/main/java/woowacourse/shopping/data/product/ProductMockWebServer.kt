package woowacourse.shopping.data.product

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object ProductMockWebServer {
    private lateinit var mockWebServer: MockWebServer
    const val PORT = 3345

    fun startServer() {
        Thread {
            mockWebServer = MockWebServer()
            mockWebServer.start(PORT)
            mockWebServer.url("/")
            mockWebServer.dispatcher = dispatcher
        }.start()
    }

    fun shutDownServer() {
        mockWebServer.close()
    }

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: return MockResponse().setResponseCode(404)
            return when {
                path.contains("/products?") -> {
                    val start = findParameterValue(path, "start").toInt()
                    val range = findParameterValue(path, "range").toInt()
                    MockResponse().setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(ProductMockWebServer.getDummyProducts(start, range))
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    private fun findParameterValue(path: String, name: String): String =
        path.substringAfter("$name=").substringBefore("&")

    private fun getDummyProducts(offset: Int, count: Int): String {
        return List(count) {
            """
                {
                    "id": ${it + 1 + offset},
                    "name": "상품${it + 1 + offset}",
                    "imageUrl": "https://mediahub.seoul.go.kr/uploads/2016/09/952e8925ec41cc06e6164d695d776e51.jpg",
                    "price": ${(it + 1) * 1000}
                }
    """
        }.joinToString(",", prefix = "[", postfix = "]").trimIndent()
    }
}
