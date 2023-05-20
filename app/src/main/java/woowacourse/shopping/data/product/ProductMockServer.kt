package woowacourse.shopping.data.product

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class ProductMockServer {

    private val mockWebServer = MockWebServer()

    val url = mockWebServer.url("").toString()

    init {
        mockWebServer.dispatcher = getDispatcher()
        mockWebServer.url("/")
    }

    fun takeRequest(): RecordedRequest {
        return mockWebServer.takeRequest()
    }

    private fun getDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.method) {
                "POST" -> {
                    when (request.path) {
                        "/products" -> MockResponse()
                            .setResponseCode(201)
                            .setBody(getPostResponse())

                        else -> MockResponse().setResponseCode(404)
                    }
                }

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
                                .setBody(getProducts(1, 200))

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

    private fun getProductById(productId: String): String {
        return """[
                {
                    "id": $productId,
                    "name": "상품$productId",
                    "imageUrl": "https://mediahub.seoul.go.kr/uploads/2016/09/952e8925ec41cc06e6164d695d776e51.jpg",
                    "price": 10000                    
                }
              ]
        """.trimIndent()
    }

    private fun getProducts(offset: Int, count: Int): String {
        return List(count) {
            """
                {
                    "id": ${it + offset + 1},
                    "name": "상품${it + offset + 1}",
                    "imageUrl": "https://mediahub.seoul.go.kr/uploads/2016/09/952e8925ec41cc06e6164d695d776e51.jpg",
                    "price": 10000
                }
    """
        }.joinToString(",", prefix = "[", postfix = "]").trimIndent()
    }

    private fun getPostResponse(): String {
        return """
        [
            {
                "id": 1,
                "name": "상품1",
                "imageUrl": "https://mediahub.seoul.go.kr/uploads/2016/09/952e8925ec41cc06e6164d695d776e51.jpg",
                "price": 10000
            }
        ]
        """.trimIndent()
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
