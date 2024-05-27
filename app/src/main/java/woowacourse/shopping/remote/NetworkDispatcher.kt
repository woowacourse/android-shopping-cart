package woowacourse.shopping.remote

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.remote.ProductMockWebServer.Companion.CONTENT_KEY
import woowacourse.shopping.remote.ProductMockWebServer.Companion.CONTENT_TYPE
import woowacourse.shopping.remote.ProductMockWebServer.Companion.GET_PAGING_PRODUCTS_PATH
import woowacourse.shopping.remote.ProductMockWebServer.Companion.GET_PRODUCT_PATH
import woowacourse.shopping.remote.ProductMockWebServer.Companion.GET_TOTAL_COUNT_PATH

class NetworkDispatcher : Dispatcher() {
    private val gson = Gson()

    override fun dispatch(request: RecordedRequest): MockResponse {
        println("request: $request")
        val path = request.path ?: return MockResponse().setResponseCode(404)
        println("path: $path")

        return when {
            path.startsWith(GET_PAGING_PRODUCTS_PATH) -> {
                val segments = path.removePrefix(GET_PAGING_PRODUCTS_PATH).toInt()
                println("segment: $segments")

                val fromIndex = (segments - 1) * ProductMockWebServer.PAGE_SIZE + 1
                val toIndex =
                    minOf(segments * ProductMockWebServer.PAGE_SIZE + 1, ProductMockWebServer.allProducts.size)
                val body =
                    ProductMockWebServer.allProducts.subList(
                        fromIndex,
                        toIndex,
                    )

                MockResponse()
                    .setHeader(CONTENT_TYPE, CONTENT_KEY)
                    .setResponseCode(200)
                    .setBody(gson.toJson(body))
            }

            path.startsWith(GET_PRODUCT_PATH) -> {
                val productId = path.removePrefix(GET_PRODUCT_PATH).toLong()
                println(" now productId: $productId")

                val body =
                    ProductMockWebServer.allProducts.find {
                        it.id == productId
                    } ?: throw NoSuchElementException()

                MockResponse()
                    .setHeader(CONTENT_TYPE, CONTENT_KEY)
                    .setResponseCode(200)
                    .setBody(gson.toJson(body))
            }

            path.startsWith(GET_TOTAL_COUNT_PATH) -> {
                val body = ProductMockWebServer.allProducts.size

                MockResponse()
                    .setHeader(CONTENT_TYPE, CONTENT_KEY)
                    .setResponseCode(200)
                    .setBody(gson.toJson(body))
            }

            else -> {
                println("not found")
                MockResponse().setResponseCode(404)
            }
        }
    }
}
