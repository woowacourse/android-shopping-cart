package woowacourse.shopping.domain.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockServer {
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
                val path = request.path ?: return MockResponse().setResponseCode(404)
                return when {
                    path == "/products" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(getProducts(0, 100))
                    }
                    path.startsWith("/products/") -> {
                        val productId = path.substringAfterLast("/")
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(getProductById(productId))
                    }
                    path.startsWith("/products") -> {
                        val parameters = getParameters(path)
                        val offset = parameters["mark"]?.toIntOrNull()
                        val count = parameters["size"]?.toIntOrNull()
                        if (offset == null || count == null) return MockResponse().setResponseCode(404)
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(getProducts(offset, count))
                    }
                    else -> {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }

        private fun getProductById(productId: String): String {
            return """[
                {
                    "id": $productId,
                    "name": "락토핏$productId",
                    "price": 10000,
                    "imageUrl": "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg"
                }
              ]
            """.trimIndent()
        }

        private fun getProducts(offset: Int, count: Int): String {
            return List(count) {
                """
                {
                    "id": ${it + offset},
                    "name": "락토핏${it + offset}",
                    "price": 10000,
                    "imageUrl": "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg"
                }
                """
            }.joinToString(",", prefix = "[", postfix = "]").trimIndent()
        }


        private fun getParameters(queryString: String): Map<String, String> {
            val parameters = mutableMapOf<String, String>()

            val query = queryString.substringAfter("?")
            val parameterPairs = query.split("&")

            for (pair in parameterPairs) {
                val (key, value) = pair.split("=")
                parameters[key] = value
            }

            return parameters
        }
    }


}
