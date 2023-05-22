package woowacourse.shopping.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockProductWebServer {
    private val mockWebServer = MockWebServer()
    val url = mockWebServer.url("").toString()

    init {
        mockWebServer.url("/")
        mockWebServer.dispatcher = getDisPatcher()
    }

    val products = List(100) {
        """{
                "id": $it,
                "name": "[사미헌]갈비탕$it",
                "price": 12000  ,
                "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
            }
            """
    }.joinToString(",", prefix = "[", postfix = "]").trimIndent()

    private fun getDisPatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: MockResponse().setHeader("Content-Type", "application/json")
                .setResponseCode(404).toString()
            return when {
                path.startsWith("/products/") -> {
                    val productId = path.substringAfterLast("/")
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(findById(productId))
                }

                path == "/products" ->
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(products)

                path.startsWith("/products") -> {
                    val offset = path.substringAfter("offset=").substringBefore("&").toInt()
                    val count = path.substringAfter("count=").toInt()
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(getNext(offset, count))
                }

                else -> MockResponse().setResponseCode(404)
            }
        }

        fun findById(id: String) = """ [
            {
                "id": $id,
                "name": "[사미헌]갈비탕$id",
                "price": 12000,
                "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
            }
        ]
        """.trimIndent()

        private fun getNext(offset: Int, count: Int): String {
            return List(count) {
                """
                {
                    "id": ${it + offset},
                    "name": "[사미헌]갈비탕${it + offset}",
                    "price": 12000,
                    "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
                }
    """
            }.joinToString(",", prefix = "[", postfix = "]").trimIndent()
        }
    }
}
