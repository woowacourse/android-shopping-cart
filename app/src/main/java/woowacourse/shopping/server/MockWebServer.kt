package woowacourse.shopping.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

fun startMockWebServer() {
    val mockWebServer = MockWebServer()
    mockWebServer.start(8080)

    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products/1" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(productsPage1)
                }
                "/products/2" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(productsPage2)
                }
                "/products/3" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(productsPage3)
                }
                "/products/4" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(productsPage4)
                }
                "/products/5" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(productsPage5)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    mockWebServer.dispatcher = dispatcher
}
