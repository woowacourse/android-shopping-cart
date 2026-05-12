package woowacourse.shopping.repository.http

import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

object ShoppingMockWebServer {
    @Volatile
    private var server: MockWebServer? = null

    private val mockProducts: List<MockProduct> =
        InMemoryProductRepository.products.toList().mapIndexed { index, product ->
            MockProduct(
                id = index + 1,
                name = product.name,
                price = product.price.value,
                imageUrl = product.imageUrl,
            )
        }

    fun baseUrl(): HttpUrl =
        synchronized(this) {
            server?.let { existingServer ->
                return existingServer.url("/")
            }

            val mockWebServer = createServer()
            mockWebServer.start()
            server = mockWebServer
            mockWebServer.url("/")
        }

    private fun createServer(): MockWebServer {
        val mockWebServer = MockWebServer()
        mockWebServer.dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    val url = request.requestUrl ?: return notFound()

                    return when {
                        url.encodedPath == "/products" -> {
                            val startIndex = url.queryParameter("startIndex")?.toIntOrNull() ?: 0
                            val pageSize = url.queryParameter("pageSize")?.toIntOrNull() ?: mockProducts.size

                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(fetchProducts(startIndex, pageSize).toJsonArray().toString())
                        }

                        url.pathSegments.size == 2 && url.pathSegments.first() == "products" -> {
                            val productId = url.pathSegments.last().toIntOrNull() ?: return notFound()
                            val product = mockProducts.firstOrNull { it.id == productId } ?: return notFound()

                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(product.toJsonObject().toString())
                        }

                        else -> notFound()
                    }
                }
            }
        return mockWebServer
    }

    private fun fetchProducts(
        startIndex: Int,
        pageSize: Int,
    ): List<MockProduct> {
        if (startIndex < 0 || startIndex >= mockProducts.size) return emptyList()
        if (pageSize <= 0) return emptyList()

        val toIndex = minOf(startIndex + pageSize, mockProducts.size)
        return mockProducts.subList(startIndex, toIndex)
    }

    private fun List<MockProduct>.toJsonArray(): JSONArray =
        JSONArray().apply {
            forEach { product ->
                put(product.toJsonObject())
            }
        }

    private fun MockProduct.toJsonObject(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("name", name)
            .put("price", price)
            .put("imageUrl", imageUrl)

    private fun notFound(): MockResponse = MockResponse().setResponseCode(404)
}

private data class MockProduct(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
