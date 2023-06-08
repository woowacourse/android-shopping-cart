package woowacourse.shopping.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class ProductMockWebServer {
    private val mockWebServer = MockWebServer()
    val url: String
        get() = "http://localhost:${1234}"

    init {
        Thread {
            mockWebServer.start(1234)
            mockWebServer.dispatcher = getDispatcher()
        }.start()
    }

    private fun getAllProducts(): String = List(100) {
        """
            {
                "id": ${it + 1},
                "name": "치킨${it + 1}",
                "price": 10000,
                "imageUrl": "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg"
            }
        """
    }.joinToString(",", prefix = "[", postfix = "]").trimIndent()

    private fun getProducts(offset: Int, count: Int): String = List(count) {
        """
            {
                "id": ${it + offset + 1},
                "name": "치킨${it + offset + 1}",
                "price": 10000,
                "imageUrl": "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg"
            }
        """
    }.joinToString(",", prefix = "[", postfix = "]").trimIndent()

    private fun getProductById(id: String): String =
        """
            {
                "id": $id,
                "name": "치킨$id",
                "price": 10000,
                "imageUrl": "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg"
            }
        """.trimIndent()

    private fun getDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.method) {
                "GET" -> {
                    val path = request.path ?: return MockResponse().setResponseCode(404)
                    when {
                        path.startsWith("/products/") -> {
                            val productId = path.substringAfterLast("/")
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(getProductById(productId))
                        }

                        path == "/products" ->
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(getAllProducts())

                        path.startsWith("/products") -> {
                            val parameters = extractQueryParameters(path)
                            val offset = parameters["offset"]?.toIntOrNull()
                            val count = parameters["count"]?.toIntOrNull()
                            if (offset == null || count == null) {
                                return MockResponse().setResponseCode(
                                    400,
                                )
                            }
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(getProducts(offset, count))
                        }

                        else -> MockResponse().setResponseCode(404)
                    }
                }

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private fun extractQueryParameters(queryString: String): Map<String, String> {
        val parameters = mutableMapOf<String, String>()

        val query = queryString.substringAfter("?")
        val pairs = query.split("&")

        for (pair in pairs) {
            val (key, value) = pair.split("=")
            parameters[key] = value
        }

        return parameters
    }
}
