package woowacourse.shopping.data.remote

import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.DUMMY_PRODUCTS

object MockServer {
    private var mockWebServer: MockWebServer? = null
    private val json = Json { encodeDefaults = true }

    fun start(port: Int = 8080, blocking: Boolean = false) {
        if (mockWebServer != null) return
        
        if (blocking) {
            val server = MockWebServer()
            server.dispatcher = dispatcher
            server.start(port)
            mockWebServer = server
        } else {
            Thread {
                try {
                    val server = MockWebServer()
                    server.dispatcher = dispatcher
                    server.start(port)
                    mockWebServer = server
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    fun stop() {
        mockWebServer?.shutdown()
        mockWebServer = null
    }

    fun getBaseUrl(): String {
        return "http://localhost:8080/"
    }

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: return MockResponse().setResponseCode(404)
            
            return when {
                path == "/products" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(json.encodeToString(DUMMY_PRODUCTS))
                }
                path.startsWith("/products/") -> {
                    val id = path.substringAfter("/products/")
                    val product = DUMMY_PRODUCTS.find { it.id == id }
                    if (product != null) {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(json.encodeToString(product))
                    } else {
                        MockResponse().setResponseCode(404)
                    }
                }
                path == "/cart-items" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody("[]")
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }
}
