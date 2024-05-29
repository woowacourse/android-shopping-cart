package woowacourse.shopping.model.data

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.model.Product
import kotlin.math.min

object ProductMockWebServerDispatcher : Dispatcher() {
    private var currentOffset = 0
    private const val LOAD_LIMIT = 20
    private val errorResponse = MockResponse().setResponseCode(404)
    private val gson = Gson()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return errorResponse

        return when {
            path.startsWith("/products/find/") -> {
                val id = path.substringAfter("/products/find/").toLong()
                val bodyItem = ProductsImpl.products.values.find { it.id == id } ?: return errorResponse
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(200)
                    .setBody(gson.toJson(bodyItem))
            }

            path.startsWith("/products/paging") -> {
                val bodyItem = getProducts()
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(200)
                    .setBody(gson.toJson(bodyItem))
            }

            path.startsWith("/products") -> {
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(200)
                    .setBody(gson.toJson(ProductsImpl.products.values))
            }

            else -> errorResponse
        }
    }

    private fun getProducts(): List<Product> {
        val fromIndex = currentOffset
        currentOffset = min(currentOffset + LOAD_LIMIT, ProductsImpl.products.values.size)
        return ProductsImpl.products.values.toList().subList(fromIndex, currentOffset)
    }
}
