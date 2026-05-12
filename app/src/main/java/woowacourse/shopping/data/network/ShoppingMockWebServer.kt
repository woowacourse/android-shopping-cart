package woowacourse.shopping.data.network

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.data.network.product.ProductDto
import woowacourse.shopping.domain.Product

fun startMockWebServer(): MockWebServer {
    val mockWebServer = MockWebServer()

    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val url = request.requestUrl
                ?: return notFound()
            return when (url.encodedPath) {
                "/products" -> {
                    val startIndex = url.queryParameter("startIndex")?.toIntOrNull()
                        ?: 0
                    val pageSize = url.queryParameter("pageSize")?.toIntOrNull()
                        ?: 20
                    val products = fetchProducts(startIndex, pageSize).map {
                        ProductDto(
                            id = it.id.toInt(),
                            name = it.name,
                            price = it.priceAmount(),
                            imageUrl = it.imageUrl,
                        )
                    }
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(Json.encodeToString(products))
                }

                "/product" -> {
                    val id = url.queryParameter("id").toString()

                    val product = MockData.MOCK_PRODUCTS.firstOrNull {
                        it.id == id
                    }
                        ?: return notFound()

                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(
                            Json.encodeToString(
                                ProductDto(
                                    id = product.id.toInt(),
                                    name = product.name,
                                    price = product.priceAmount(),
                                    imageUrl = product.imageUrl,
                                ),
                            ),
                        )
                }

                else -> notFound()
            }
        }
    }

    mockWebServer.dispatcher = dispatcher
    mockWebServer.start(12345)

    return mockWebServer
}

private fun fetchProducts(
    startIndex: Int,
    pageSize: Int,
): List<Product> {
    val size = MockData.MOCK_PRODUCTS.size
    if (startIndex !in 0..size || pageSize <= 0) return emptyList()

    val toOffset = minOf(startIndex + pageSize, size)
    return MockData.MOCK_PRODUCTS.subList(startIndex, toOffset)
}

private fun notFound(): MockResponse = MockResponse().setResponseCode(404)
