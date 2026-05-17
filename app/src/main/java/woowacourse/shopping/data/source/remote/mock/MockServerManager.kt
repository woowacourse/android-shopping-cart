package woowacourse.shopping.data.source.remote.mock

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.source.ProductData
import woowacourse.shopping.data.source.remote.model.ProductResponse

object MockServerManager {
    private val server = MockWebServer()
    private val json = Json { ignoreUnknownKeys = true }

    fun start() {
        Thread {
            server.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    val path = request.path ?: ""
                    return when {
                        path == "/products/count" -> {
                            MockResponse()
                                .setResponseCode(200)
                                .setBody(ProductData.products.size.toString())
                        }
                        path.startsWith("/products/") && !path.contains("?") -> {
                            val productId = path.substringAfterLast("/")
                            val product = ProductData.products
                                .find { it.id == productId }
                                ?.let { ProductResponse(it.id, it.name, it.price.value, it.imageUrl) }

                            if (product != null) {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(200)
                                    .setBody(json.encodeToString(product))
                            } else {
                                MockResponse().setResponseCode(404)
                            }
                        }
                        path.startsWith("/products") -> {
                            val start = request.requestUrl?.queryParameter("start")?.toInt() ?: 0
                            val count = request.requestUrl?.queryParameter("count")?.toInt() ?: 20

                            val products = ProductData.products
                                .drop(start)
                                .take(count)
                                .map { ProductResponse(it.id, it.name, it.price.value, it.imageUrl) }

                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(json.encodeToString(products))
                        }
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            server.start(12345)
        }.start()
    }

    fun getBaseUrl(): String = "http://localhost:12345/"
}
