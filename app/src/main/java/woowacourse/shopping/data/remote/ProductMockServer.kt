package woowacourse.shopping.data.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.util.mockData

class ProductMockServer {
    private lateinit var mockWebServer: MockWebServer
    private val productDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: return MockResponse().setResponseCode(FAIL_CODE)

            return when (path) {
                "/products" -> {
                    val parameters = parseParameters(request.body.readUtf8())
                    val unit = parameters[parameters.keys.first()]?.toIntOrNull()
                    val lastIndex = parameters[parameters.keys.last()]?.toIntOrNull()
                    if (unit == null || lastIndex == null) {
                        return MockResponse().setResponseCode(FAIL_CODE)
                    }

                    MockResponse().setHeader("Content-Type", "application/json")
                        .setResponseCode(SUCCESS_CODE).setBody(getProducts(unit, lastIndex))
                }

                else -> {
                    MockResponse().setResponseCode(FAIL_CODE)
                }
            }
        }
    }

    private fun getProducts(unit: Int, startIndex: Int): String {
        val endIndex = minOf(startIndex + unit, mockData.size)
        val products = mockData.subList(startIndex, endIndex)

        return products.joinToString(",\n", prefix = "[\n", postfix = "\n]") { it }
    }

    private fun parseParameters(requestParams: String): Map<String, String> {
        val parameters = mutableMapOf<String, String>()

        val params = requestParams.split("&")
        for (param in params) {
            val (key, value) = param.split("=")
            parameters[key] = value
        }

        return parameters
    }

    fun initMockWebServer() {
        Thread {
            mockWebServer = MockWebServer()
            mockWebServer.apply {
                start(PORT)
                dispatcher = productDispatcher
            }
        }.apply {
            start()
            join()
        }
    }

    companion object {
        const val PORT = 6666
        private const val FAIL_CODE = 404
        private const val SUCCESS_CODE = 200
    }
}
