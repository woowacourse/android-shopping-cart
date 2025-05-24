package woowacourse.shopping.fixture

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object MockWebServerBuilder {
    private val INSTANCE = MockWebServer()
    val dispatcher =
        object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/products?page=1&size=20" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(TestProducts.products)
                    }
                    "/products?page=2&size=20" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(TestProducts.products)
                    }
                    "/total-size" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody("30")
                    }
                    else -> {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }

    fun build(): MockWebServer {
        INSTANCE.dispatcher = dispatcher
        INSTANCE.start()
        return INSTANCE
    }
}
