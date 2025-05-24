package woowacourse.shopping.data.remote

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.DummyProducts

object ProductMockWebServerDispatcher : Dispatcher() {
    private val errorResponse = MockResponse().setResponseCode(404)
    private val gson = Gson()
    private val allProducts = DummyProducts.values.toList()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return errorResponse

        return when {
            path.startsWith("/products/find/") -> handleFindById(path)
            path.startsWith("/products/paging") -> handlePaging(request)
            path.startsWith("/products") -> respondWithAllProducts()
            else -> errorResponse
        }
    }

    private fun handleFindById(path: String): MockResponse {
        val idString = path.substringAfter("/products/find/")
        val id = idString.toLongOrNull() ?: return errorResponse

        val product = allProducts.find { it.productId == id } ?: return errorResponse

        return MockResponse()
            .setHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(gson.toJson(product))
    }

    private fun handlePaging(request: RecordedRequest): MockResponse {
        val httpUrl = request.requestUrl ?: return errorResponse

        val offset = httpUrl.queryParameter("offset")?.toIntOrNull() ?: return errorResponse
        val pageSize = httpUrl.queryParameter("pageSize")?.toIntOrNull() ?: return errorResponse

        if (offset > allProducts.lastIndex) {
            return emptyListResponse()
        }

        val pagedProducts = allProducts.drop(offset).take(pageSize)

        return jsonResponse(pagedProducts)
    }

    private fun respondWithAllProducts() = jsonResponse(allProducts)

    private fun jsonResponse(body: Any) =
        MockResponse()
            .setHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(gson.toJson(body))

    private fun emptyListResponse() = jsonResponse(emptyList<Any>())
}
