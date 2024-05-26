package woowacourse.shopping.data.product.server

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.product.dummyProducts
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.product.server.ProductServerApiPath.CONTENT_KEY
import woowacourse.shopping.data.product.server.ProductServerApiPath.CONTENT_TYPE
import woowacourse.shopping.data.product.server.ProductServerApiPath.FIND_PRODUCT_PAGE_PATH
import woowacourse.shopping.data.product.server.ProductServerApiPath.FIND_PRODUCT_PATH
import woowacourse.shopping.data.product.server.ProductServerApiPath.TOTAL_COUNT_PATH
import kotlin.math.min

class MockWebProductServerDispatcher(private val products: List<Product> = dummyProducts) :
    Dispatcher() {
    private val gson = Gson()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return makeErrorResponse()

        return when {
            path.startsWith(FIND_PRODUCT_PATH) -> {
                val productId = path.removePrefix(FIND_PRODUCT_PATH).toLong()
                val product = products.find { it.id == productId } ?: return makeErrorResponse()
                makeSuccessResponse(product)
            }

            path.startsWith(FIND_PRODUCT_PAGE_PATH) -> {
                val (page, pageSize) =
                    path.removePrefix(FIND_PRODUCT_PAGE_PATH)
                        .split("/")
                        .map { it.toInt() }

                val fromIndex = page * pageSize
                val toIndex = min(fromIndex + pageSize, products.size)
                val pageProducts = products.subList(fromIndex, toIndex)
                makeSuccessResponse(pageProducts)
            }

            path.startsWith(TOTAL_COUNT_PATH) -> makeSuccessResponse(products.size)

            else -> makeErrorResponse()
        }
    }

    private fun <T> makeSuccessResponse(body: T): MockResponse {
        return MockResponse()
            .setHeader(CONTENT_TYPE, CONTENT_KEY)
            .setResponseCode(200)
            .setBody(gson.toJson(body))
    }

    private fun makeErrorResponse(): MockResponse {
        return MockResponse().setResponseCode(404)
    }
}
