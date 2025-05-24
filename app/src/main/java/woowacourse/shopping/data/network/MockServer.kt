package woowacourse.shopping.data.network

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.GoodsStorage

class MockServer {
    private val gson = Gson()
    private val goodsStorage = GoodsStorage()
    private val mockWebServer = MockWebServer()

    fun startMockWebServer() {
        mockWebServer.dispatcher = createDispatcher()

        mockWebServer.start(8888)
    }

    private fun createDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.requestUrl?.encodedPath) {
                    "/products" -> handleProductRequest(request)
                    "/products/page" -> handlePagedProductsRequest(request)
                    else -> MockResponse().setResponseCode(NOT_FOUND)
                }
            }
        }
    }

    private fun handleProductRequest(request: RecordedRequest): MockResponse {
        val idParam = request.requestUrl?.queryParameter(PARAM_ID)?.toIntOrNull()
        val matchedProduct =
            goodsStorage.getGoodsById(idParam)
                ?: return MockResponse()
                    .setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .setResponseCode(NOT_FOUND)

        val body = gson.toJson(matchedProduct)

        return MockResponse()
            .setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
            .setResponseCode(OK)
            .setBody(body)
    }

    private fun handlePagedProductsRequest(request: RecordedRequest): MockResponse {
        val page = request.requestUrl?.queryParameter(PARAM_PAGE)?.toIntOrNull()
        val count = request.requestUrl?.queryParameter(PARAM_COUNT)?.toIntOrNull()

        if (page == null || count == null) {
            return MockResponse()
                .setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
                .setResponseCode(NOT_FOUND)
        }

        val fromIndex = page * count
        val toIndex = minOf(fromIndex + count, goodsStorage.dummyData.size)
        val pagedProducts = goodsStorage.getPage(fromIndex, toIndex)
        val body = gson.toJson(pagedProducts)

        return MockResponse()
            .setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
            .setResponseCode(OK)
            .setBody(body)
    }

    companion object {
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_JSON = "application/json"

        private const val OK: Int = 200
        private const val NOT_FOUND: Int = 404

        private const val PARAM_ID: String = "id"
        private const val PARAM_PAGE: String = "page"
        private const val PARAM_COUNT: String = "count"
    }
}
