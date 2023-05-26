package woowacourse.shopping.data

import com.example.domain.datasource.firstPageProducts
import com.example.domain.datasource.secondPageProducts
import com.example.domain.datasource.thirdPageProducts
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockServer {
    private lateinit var mockWebServer: MockWebServer
    private var _baseUrl: String = ""
    val baseUrl get() = _baseUrl

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products?page=1" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(firstPageProducts)
                }

                "/products?page=2" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(secondPageProducts)
                }

                "/products?page=3" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(thirdPageProducts)
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
