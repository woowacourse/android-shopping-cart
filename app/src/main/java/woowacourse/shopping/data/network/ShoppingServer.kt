package woowacourse.shopping.data.network

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.dummy.ProductData

class ShoppingServer {
    private val server = MockWebServer()
    private val gson = Gson()

    fun startMockWebServer() {
        server.dispatcher = createDispatcher()
        server.start(8888)
    }

    private fun createDispatcher(): Dispatcher =
        object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                when (request.requestUrl?.encodedPath) {
                    "/products" -> handleProductRequest(request)
                    "/products/page" -> handlePagedProductsRequest(request)
                    else -> MockResponse().setResponseCode(FAILURE_RESPONSE_CODE)
                }
        }

    private fun handleProductRequest(request: RecordedRequest): MockResponse {
        val id =
            request.requestUrl?.queryParameter(PARAM_ID)?.toLongOrNull()
                ?: return failureResponse()

        val product = ProductData.getProductById(id) ?: return failureResponse()
        val body = gson.toJson(product)
        return successResponse(body)
    }

    private fun handlePagedProductsRequest(request: RecordedRequest): MockResponse {
        val limit = request.requestUrl?.queryParameter(PARAM_LIMIT)?.toIntOrNull()
        val offset = request.requestUrl?.queryParameter(PARAM_OFFSET)?.toIntOrNull()
        if (limit == null || offset == null) {
            return failureResponse()
        }

        val pagedProducts = ProductData.getPagedProducts(limit, offset)
        val body = gson.toJson(pagedProducts)
        return successResponse(body)
    }

    private fun failureResponse(): MockResponse =
        MockResponse()
            .setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
            .setResponseCode(FAILURE_RESPONSE_CODE)

    private fun successResponse(body: String): MockResponse =
        MockResponse()
            .setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
            .setResponseCode(SUCCESS_RESPONSE_CODE)
            .setBody(body)

    companion object {
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_JSON = "application/json"

        private const val SUCCESS_RESPONSE_CODE: Int = 200
        private const val FAILURE_RESPONSE_CODE: Int = 404

        const val PARAM_ID: String = "id"
        const val PARAM_LIMIT: String = "limit"
        const val PARAM_OFFSET: String = "offset"
    }
}
