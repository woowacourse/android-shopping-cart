package woowacourse.shopping.database.product

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockServer {

    private lateinit var mockWebServer: MockWebServer

    val port
        get() = mockWebServer.port

    fun run() {
        val thread = Thread {
            if (::mockWebServer.isInitialized) return@Thread
            mockWebServer = MockWebServer()
            mockWebServer.url("/")
            mockWebServer.dispatcher = makeProductMockDispatcher()
        }
        thread.start()
    }

    private fun makeProductMockDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path
            if (path.isNullOrBlank()) {
                return MockResponse().setResponseCode(400)
            }

            return when {
                REGEX_REQUEST_PRODUCT.matches(path) -> {
                    val productId = path.removePrefix("/products/").toInt() - 1
                    MockResponse().setResponseCode(200)
                        .setBody(PRODUCT_JSON_LIST[productId])
                }

                REGEX_REQUEST_PRODUCTS.matches(path) -> {
                    val limit = findParameterValue(path, "limit").toInt()
                    val offset = findParameterValue(path, "offset").toInt()
                    val products = PRODUCT_JSON_LIST.slice(offset until PRODUCT_JSON_LIST.size)
                        .take(limit)
                    val body = products.joinToString(
                        prefix = "[",
                        postfix = "]",
                        separator = ",\n"
                    ) { it }
                    MockResponse().setResponseCode(200).setBody(body)
                }

                REGEX_REQUEST_PRODUCTS_COUNT.matches(path) -> {
                    MockResponse().setResponseCode(200)
                        .setBody("{ \"count\": ${PRODUCT_JSON_LIST.size} }")
                }

                else -> {
                    MockResponse().setResponseCode(400)
                }
            }
        }
    }

    private fun findParameterValue(path: String, name: String): String =
        path.substringAfter("$name=").substringBefore("&")

    companion object {
        private val REGEX_REQUEST_PRODUCT = Regex("^/products/\\d+$")
        private val REGEX_REQUEST_PRODUCTS = Regex("^/products\\?limit=\\d+&offset=\\d+$")
        private val REGEX_REQUEST_PRODUCTS_COUNT = Regex("^/products/count")

        private val PRODUCT_JSON_LIST: List<String> = (1..102).map { index ->
            """
            {  
                "id": $index,
                "imageUrl": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvcCPDO5TRkS6bHmemx0262nWeXizH3fD8fJPsLHc2GxDqKCqMWeOYFK3HOJu5VKpaAH0&usqp=CAU",
                "name": "Product $index",
                "price": 10000
            }
        """.trimIndent()
        }
    }
}