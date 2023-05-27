package woowacourse.shopping.data

import android.content.Context
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockServer(
    context: Context
) {
    private lateinit var mockWebServer: MockWebServer
    private var _baseUrl: String = ""
    val baseUrl get() = _baseUrl

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products?page=1" -> {
                    val data = context.assets.open("FirstPageProducts.json").reader().readText()
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(data)
                }

                "/products?page=2" -> {
                    val data = context.assets.open("SecondPageProducts.json").reader().readText()
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(data)
                }

                "/products?page=3" -> {
                    val data = context.assets.open("ThirdPageProducts.json").reader().readText()
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(data)
                }

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    init {
        synchronized(this) {
            if (!::mockWebServer.isInitialized) {
                val thread = Thread {
                    mockWebServer = MockWebServer()
                    mockWebServer.url("/")
                    mockWebServer.dispatcher = dispatcher
                    _baseUrl = String.format("http://localhost:%s", mockWebServer.port)
                }
                thread.start()
                thread.join()
            }
        }
    }
}
