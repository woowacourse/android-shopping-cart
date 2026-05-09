package woowacourse.shopping.data.remote

import android.util.Log
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.mock.MockData

object MockWebServerProvider {
    private const val TAG = "MockWebServerProvider"
    private const val PORT = 12345
    const val BASE_URL = "http://localhost:$PORT/"

    private var server: MockWebServer? = null

    fun start() {
        if (server != null) return
        runCatching {
            MockWebServer().apply {
                dispatcher = createDispatcher()
                start(PORT)
            }
        }.onSuccess { startedServer ->
            server = startedServer
        }.onFailure { throwable ->
            Log.e(TAG, "Failed to start mock web server on port $PORT", throwable)
        }
    }

    fun shutdown() {
        server?.shutdown()
        server = null
    }

    private fun createDispatcher(): Dispatcher = object : Dispatcher() {
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

    private fun productsJson(): String =
        MockData.products.joinToString(prefix = "[", postfix = "]") { it.toJson() }

    private fun productJson(id: String): String? =
        MockData.products.firstOrNull { it.id == id }?.toJson()

    private fun woowacourse.shopping.domain.product.Product.toJson(): String = """
        {
            "id": $id,
            "name": "${name.value}",
            "price": ${price.value},
            "imageUrl": "${imageUrl.value}"
        }
    """.trimIndent()
}
