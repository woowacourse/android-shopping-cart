package woowacourse.shopping.data.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.domain.product.Product

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
                    path == "/products" -> {
                        jsonResponse(
                            MockProductResponseFactory.productsJson(
                                MockProductSeedData.products,
                            ),
                        )
                    }

                    path.startsWith("/products/") -> {
                        val id = path.removePrefix("/products/")

                        val product =
                            MockProductSeedData.products
                                .firstOrNull { it.id == id }
                                ?: return notFound()

                        jsonResponse(
                            MockProductResponseFactory.productJson(product),
                        )
                    }

                    else -> notFound()
                }
            }
        }

    private fun jsonResponse(body: String): MockResponse =
        MockResponse()
            .setHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(body)

    private fun notFound(): MockResponse =
        MockResponse()
            .setResponseCode(404)
}
