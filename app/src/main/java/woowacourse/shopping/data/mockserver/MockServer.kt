package woowacourse.shopping.data.mockserver

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.dummyProducts

fun startMockServer(): MockWebServer {
    val server = MockWebServer()
    val gson = Gson()

    val productListJson = gson.toJson(dummyProducts)

    val dispatcher =
        object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                when (request.path) {
                    "/products" ->
                        MockResponse()
                            .setResponseCode(200)
                            .setHeader("Content-Type", "application/json")
                            .setBody(productListJson)

                    else -> MockResponse().setResponseCode(404)
                }
        }

    server.dispatcher = dispatcher
    server.start()

    return server
}
