package woowacourse.shopping.data.mockserver

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.mockserver.product.ProductResponseJson

object ShoppingCartMockServer {
    private val mockWebServer = MockWebServer()

    const val BASE_URL = "http://localhost:55555/"

    fun getMockResponse(responseJson: String?): MockResponse = if (responseJson != null) {
        MockResponse()
            .setHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(responseJson)
    } else {
        MockResponse().setResponseCode(404)
    }

    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return getMockResponse(ProductResponseJson().productResponseTable[request.path])
        }
    }

    init {
        mockWebServer.start(55555)
        mockWebServer.dispatcher = dispatcher
    }
}
