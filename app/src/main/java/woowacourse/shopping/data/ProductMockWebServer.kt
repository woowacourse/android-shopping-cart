package woowacourse.shopping.data

import com.domain.datasource.products
import com.domain.model.Product
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class ProductMockWebServer {
    private lateinit var mockWebServer: MockWebServer

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(products)
                }

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    fun request(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        synchronized(this) {
            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcher
            mockWebServer.url("/")

            val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
            val url = "$baseUrl/products"

            ProductService.request(url, onSuccess, onFailure)
        }
    }
}
