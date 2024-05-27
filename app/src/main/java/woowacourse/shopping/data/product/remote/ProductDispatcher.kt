package woowacourse.shopping.data.product.remote

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.product.dummyProducts
import woowacourse.shopping.model.Product
import kotlin.math.min

object ProductDispatcher : Dispatcher() {
    private val gson = Gson()
    private const val SUCCESS_CODE = 200
    private const val FAILURE_CODE = 404
    private val errorResponseCode = MockResponse().setResponseCode(FAILURE_CODE)
    private var page: Int = 0

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return errorResponseCode

        return when {
            path.startsWith(PRODUCT_FIND_RANGE_PATH) -> {
                val products = findRange(page++, pageSize = 20)
                MockResponse()
                    .setHeader("Content-Type", "application/json".toMediaType())
                    .setResponseCode(SUCCESS_CODE)
                    .setBody(gson.toJson(products))
            }
            path.startsWith(PRODUCT_FIND_PATH) -> {
                val id = path.split("/").last().toLongOrNull() ?: return errorResponseCode
                val product: Product? = dummyProducts.find { it.id == id }
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(SUCCESS_CODE)
                    .setBody(gson.toJson(product))
            }
            path.startsWith(PRODUCT_FIND_ALL_PATH) -> {
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(SUCCESS_CODE)
                    .setBody(gson.toJson(dummyProducts))
            }
            else -> {
                MockResponse().setResponseCode(404)
            }
        }
    }

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, dummyProducts.size)
        return if (fromIndex in 0 until toIndex) {
            dummyProducts.subList(fromIndex, toIndex)
        } else {
            emptyList()
        }
    }
}
