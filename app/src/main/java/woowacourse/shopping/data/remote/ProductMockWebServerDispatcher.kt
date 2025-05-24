package woowacourse.shopping.data.remote

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.model.Product
import kotlin.math.min

object ProductMockWebServerDispatcher : Dispatcher() {
    private var currentOffset = 0
    private const val LOAD_LIMIT = 10
    private val errorResponse = MockResponse().setResponseCode(404)
    private val gson = Gson()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return errorResponse

        return when {
            path.startsWith("/products/find/") -> {
                val id = path.substringAfter("/products/find/").toLong()
                val bodyItem =
                    DummyProducts.values.find { it.productId == id } ?: return errorResponse
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(200)
                    .setBody(gson.toJson(bodyItem))
            }

            path.startsWith("/products/paging") -> {
                val httpUrl = request.requestUrl ?: return errorResponse
                val offset = httpUrl.queryParameter("offset")?.toIntOrNull() ?: return errorResponse
                val pageSize =
                    httpUrl.queryParameter("pageSize")?.toIntOrNull() ?: return errorResponse

                val products = DummyProducts.values.toList()
                if (offset > products.lastIndex) {
                    return MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody("[]")
                }

                val subList = products.drop(offset).take(pageSize)

                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(200)
                    .setBody(gson.toJson(subList))
            }

            path.startsWith("/products") -> {
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(200)
                    .setBody(gson.toJson(DummyProducts.values))
            }

            else -> errorResponse
        }
    }

    private fun subProducts(): List<Product> {
        val fromIndex = currentOffset
        currentOffset = min(currentOffset + LOAD_LIMIT, DummyProducts.values.size)
        return DummyProducts.values.toList().subList(fromIndex, currentOffset)
    }
}
