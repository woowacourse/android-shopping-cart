package woowacourse.shopping.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.db.firstPageProducts

class ProductMockWebServer : Thread() {
    fun getDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products" -> {
                    MockResponse()
                        .setHeader(CONTENT_TYPE, JSON_MEDIA_TYPE)
                        .setResponseCode(200)
                        .setBody(firstPageProducts)
                }
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    companion object {
        private const val JSON_MEDIA_TYPE = "application/json"
        private const val CONTENT_TYPE = "Content-Type"
    }
}
