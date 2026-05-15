package woowacourse.shopping.data.source.remote

import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest

object ShoppingMockServer {
    private val server = MockWebServer()

    val baseUrl: String
        get() = server.url("/").toString()

    fun start() {
        Thread {
            server.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        val path = request.url.encodedPath

                        return when (path) {
                            "/products" ->
                                MockResponse
                                    .Builder()
                                    .code(200)
                                    .addHeader("Content-Type", "application/json")
                                    .body(ProductJsonData.products)
                                    .build()

                            else ->
                                MockResponse
                                    .Builder()
                                    .code(404)
                                    .build()
                        }
                    }
                }
            server.start()
        }.start()
    }
}
