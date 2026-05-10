package woowacourse.shopping.data.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.mock.MockProductSeedData

object MockWebServerProvider {
    private var server: MockWebServer? = null
    val baseUrl: String
        get() {
            ensureStarted()
            return requireNotNull(server).url("/").toString()
        }

    @Synchronized
    private fun ensureStarted() {
        if (server != null) return
        server =
            MockWebServer().apply {
                dispatcher = createDispatcher()
                start()
            }
    }

    private fun createDispatcher(): Dispatcher =
        object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path ?: return notFound()
                return when {
                    path == "/products" -> jsonResponse(productsJson())
                    path.startsWith("/products/") -> {
                        val id = path.removePrefix("/products/")
                        val productJson = productJson(id) ?: return notFound()
                        jsonResponse(productJson)
                    }
                    path == "/cart-items" -> jsonResponse("[]")
                    else -> notFound()
                }
            }
        }

    private fun jsonResponse(body: String): MockResponse =
        MockResponse()
            .setHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(body)

    private fun notFound(): MockResponse = MockResponse().setResponseCode(404)

    private fun productsJson(): String = MockProductSeedData.products.joinToString(prefix = "[", postfix = "]") { it.toJson() }

    private fun productJson(id: String): String? = MockProductSeedData.products.firstOrNull { it.id == id }?.toJson()

    private fun woowacourse.shopping.domain.product.Product.toJson(): String =
        """
        {
            "id": $id,
            "name": "${name.value}",
            "price": ${price.value},
            "imageUrl": "${imageUrl.value}"
        }
        """.trimIndent()
}
