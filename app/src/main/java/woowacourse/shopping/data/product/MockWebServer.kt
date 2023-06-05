package woowacourse.shopping.data.product

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object MockWebServer {

    private var dispatcherSetting: Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products?lastProductId=0" -> {
                    MockResponse()
                        .setHeader("Contet-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(firstJsonProducts)
                }
                "/products?lastProductId=20" -> {
                    MockResponse()
                        .setHeader("Contet-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(secondJsonProducts)
                }
                "/products?lastProductId=40" -> {
                    MockResponse()
                        .setHeader("Contet-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(thirdJsonProducts)
                }
                else -> {
                    if (request.path!!.startsWith("/products?id")) {
                        val parts = request.path!!.split("/")
                        val lastPart = parts.last()
                        val numberParts = lastPart.split("=")
                        val number = numberParts.last().toLong()

                        MockResponse()
                            .setHeader("Contet-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(jsonMap[number]!!)
                    } else {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }
    }

    private val mockWebServer = MockWebServer().apply {
        url("/")
        dispatcher = dispatcherSetting
    }

    fun get(): MockWebServer = mockWebServer
}
