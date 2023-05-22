package woowacourse.shopping.data.mockserver

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.mockserver.product.ProductResponseJson

class ShoppingCartMockServer : Thread() {
    private val mockWebServer = MockWebServer()
    lateinit var BASE_URL: String
    val okHttpClient = OkHttpClient()
    val gson = Gson()

    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return getMockResponse(ProductResponseJson().productResponseTable[request.path])
        }
    }

    fun getMockResponse(responseJson: String?): MockResponse = if (responseJson != null) {
        MockResponse()
            .setHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(responseJson)
    } else {
        MockResponse().setResponseCode(404)
    }

    override fun run() {
        mockWebServer.url("/")
        mockWebServer.dispatcher = dispatcher
        BASE_URL = String.format("http://localhost:%s", mockWebServer.port)
    }
}
