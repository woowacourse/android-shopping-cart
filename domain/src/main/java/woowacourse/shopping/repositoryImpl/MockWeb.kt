package woowacourse.shopping.repositoryImpl

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockWeb {
    private val mockWebServer = MockWebServer()

    val url = mockWebServer.url("").toString()

    init {
        mockWebServer.dispatcher = dispatcher
        mockWebServer.url("/")
    }

    fun takeRequest(): RecordedRequest {
        return mockWebServer.takeRequest()
    }

    companion object {
        val dispatcher = object : Dispatcher() {
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
                                    .setBody(getProducts(0, 100))

                            path.startsWith("/products") -> {
                                val parameters = extractQueryParameters(path)
                                val offset = parameters["offset"]?.toIntOrNull()
                                val count = parameters["count"]?.toIntOrNull()
                                if (offset == null || count == null) {
                                    return MockResponse().setResponseCode(
                                        400
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
                    "name": "치킨$productId",
                    "price": 10000,
                    "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
                }
              ]
            """.trimIndent()
        }

        private fun getProducts(offset: Int, count: Int): String {
            return List(count) {
                """
                {
                    "id": ${it + offset},
                    "name": "치킨${it + offset}",
                    "price": 10000,
                    "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
                }
    """
            }.joinToString(",", prefix = "[", postfix = "]").trimIndent()
        }

        private fun getPostResponse(): String {
            return """
        [
            {
                "id": 1,
                "name": "치킨",
                "price": 10000,
                "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
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
}
