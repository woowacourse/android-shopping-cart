package woowacourse.shopping.data.remote.mock

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.remote.mapper.toResponse

object MockWebServerProvider {
    private val mockWebServer = MockWebServer()
    private val json = Json { ignoreUnknownKeys = true }

    fun start(): String =
        runBlocking(Dispatchers.IO) {
            val dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse =
                        when {
                            request.path == "/products" -> {
                                val products = ProductFixture.productList.map { it.toResponse() }

                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(200)
                                    .setBody(json.encodeToString(products))
                            }

                            request.path?.startsWith("/products/") == true -> {
                                val productId =
                                    request.path
                                        ?.substringAfterLast("/")
                                        ?.toIntOrNull()

                                val product = ProductFixture.productList.firstOrNull { it.productId == productId }

                                if (product == null) {
                                    MockResponse().setResponseCode(404)
                                } else {
                                    MockResponse()
                                        .setHeader("Content-Type", "application/json")
                                        .setResponseCode(200)
                                        .setBody(json.encodeToString(product.toResponse()))
                                }
                            }

                            else -> {
                                MockResponse().setResponseCode(404)
                            }
                        }
                }

            mockWebServer.dispatcher = dispatcher

            try {
                mockWebServer.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mockWebServer.url("/").toString()
        }

    fun shutdown() {
        mockWebServer?.shutdown()
    }
}
