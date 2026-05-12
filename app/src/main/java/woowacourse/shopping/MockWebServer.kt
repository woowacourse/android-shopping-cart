package woowacourse.shopping

import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest

fun startMockWebServer(): MockWebServer {
    val mockWebServer = MockWebServer()

    val dispatcher =
        object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                when {
                    request.url.encodedPath == "/products" -> {
                        val offset = request.url.queryParameter("offset")?.toIntOrNull() ?: 0
                        val size = request.url.queryParameter("size")?.toIntOrNull() ?: 20
                        val productsJson =
                            (1..30).drop(offset).take(size).joinToString(
                                prefix = "[",
                                postfix = "]",
                                separator = ",",
                            ) { index ->
                                productJson(index)
                            }

                        MockResponse
                            .Builder()
                            .code(200)
                            .setHeader("Content-Type", "application/json")
                            .body(productsJson)
                            .build()
                    }

                    request.url.encodedPath == "/products/size" -> {
                        MockResponse
                            .Builder()
                            .code(200)
                            .setHeader("Content-Type", "application/json")
                            .body("{\"size\": 30}")
                            .build()
                    }

                    request.url.encodedPath.startsWith("/product/") -> {
                        val productId =
                            request.url.encodedPath
                                .removePrefix("/product/")
                                .toIntOrNull()

                        if (productId in 1..30) {
                            MockResponse
                                .Builder()
                                .code(200)
                                .setHeader("Content-Type", "application/json")
                                .body(productJson(productId!!))
                                .build()
                        } else {
                            MockResponse
                                .Builder()
                                .code(404)
                                .build()
                        }
                    }

                    else -> {
                        MockResponse
                            .Builder()
                            .code(404)
                            .build()
                    }
                }
        }

    mockWebServer.dispatcher = dispatcher
    return mockWebServer
}

private fun productJson(productId: Int): String =
    """
    {
        "id": $productId,
        "name": "아메리카노$productId",
        "price": ${productId * 1000},
        "imageUrl": "https://bizimg.giftishow.com/Resource/goods/2025/G00003320983/G00003320983.jpg"
    }
    """.trimIndent()
