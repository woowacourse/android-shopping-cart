package woowacourse.shoppoing

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

fun startMockWebServer(): MockWebServer {
    val mockWebServer = MockWebServer()
    mockWebServer.url("/")
    val products = """
                [
                    {
                        "id": 1,
                        "name": "치킨",
                        "price": 10000,
                        "imageUrl": "http://example.com/chicken.jpg"
                    },
                    {
                        "id": 2,
                        "name": "피자",
                        "price": 20000,
                        "imageUrl": "http://example.com/pizza.jpg"
                    }
                ]
    """.trimIndent()

    val nextProducts1 = List(10) {
        """
                {
                    "id": ${it + 1},
                    "name": "치킨",
                    "price": 10000,
                    "imageUrl": "http://example.com/chicken.jpg"
                }"""
    }.joinToString(",", prefix = "[", postfix = "]").trimIndent()

    val nextProducts2 = List(10) {
        """
                {
                    "id": ${it + 11},
                    "name": "치킨",
                    "price": 10000,
                    "imageUrl": "http://example.com/chicken.jpg"
                }
    """
    }.joinToString(",", prefix = "[", postfix = "]").trimIndent()

    val product = """[
                {
                    "id": 1,
                    "name": "치킨",
                    "price": 10000,
                    "imageUrl": "http://example.com/chicken.jpg"
                }]
    """.trimIndent()

    val fakeProducts = List(100) {
        """
                {
                    "id": ${it + 1},
                    "name": "치킨",
                    "price": 10000,
                    "imageUrl": "http://example.com/chicken.jpg"
                }
    """
    }

    val postResponse = """
                [{
                    "id": 999
                }]
    """.trimIndent()

    val insertedResponse = """
                [{
                    "id": 999,
                    "name": "치킨",
                    "price": 10000,
                    "imageUrl": "http://example.com/chicken.jpg"
                }]
    """.trimIndent()

    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            request.path?.let { string ->
                val parameters = kotlin.runCatching { extractQueryParameters(string) }.getOrNull()
                    ?: return@let
                val offset = parameters["offset"]?.toIntOrNull()
                val count = parameters["count"]?.toIntOrNull()
                if (offset != null && count != null) {
                    return MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(
                            fakeProducts.subList(offset, offset + count)
                                .joinToString(",", prefix = "[", postfix = "]")
                                .trimIndent()
                        )
                }
            }
            return when (request.method) {
                "POST" -> {
                    when (request.path) {
                        "/products" -> MockResponse()
                            .setResponseCode(201)
                            .setBody(postResponse)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
                "GET" -> {
                    when (request.path) {
                        "/products" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(products)
                        }
                        "/products?offset=0&count=10" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(nextProducts1)
                        }
                        "/products?offset=10&count=10" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(nextProducts2)
                        }
                        "/products/1" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(product)
                        }
                        "/products/999" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(insertedResponse)
                        }
                        else -> {
                            MockResponse().setResponseCode(404)
                        }
                    }
                }

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    mockWebServer.dispatcher = dispatcher
    return mockWebServer
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
