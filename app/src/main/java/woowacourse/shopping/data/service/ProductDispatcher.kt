package woowacourse.shopping.data.service

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.entity.ProductEntity
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_OK

class ProductDispatcher : Dispatcher() {
    private val gson = Gson()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return MockResponse().setResponseCode(HTTP_NOT_FOUND)

        return when {
            path.startsWith("/products/") -> handleProductsPath(path)
            path.startsWith("/product/") -> handelProductOfPath(path)
            path == "/products" -> response(ProductEntity.STUB_LIST)
            else -> MockResponse().setResponseCode(HTTP_NOT_FOUND)
        }
    }

    private fun handelProductOfPath(path: String): MockResponse {
        val id = extractProductId(path) ?: return MockResponse().setResponseCode(HTTP_NOT_FOUND)
        return try {
            val response = gson.toJson(ProductEntity.STUB_LIST.first { it.id == id })
            MockResponse()
                .setResponseCode(HTTP_OK)
                .setHeader("Content-Type", "application/json".toMediaType())
                .setBody(response)
        } catch (e: Exception) {
            MockResponse().setResponseCode(HTTP_NOT_FOUND)
        }
    }

    private fun extractProductId(path: String): Long? {
        val regex = "/product/(\\d+)".toRegex()
        val matchResult = regex.matchEntire(path) ?: return null
        return matchResult.groupValues[1].toLongOrNull()
    }

    private fun handleProductsPath(path: String): MockResponse {
        val (index, pageSize) =
            extractIndexAndPageSize(path)
                ?: return MockResponse().setResponseCode(HTTP_NOT_FOUND)
        return response(getProductSubList(index, pageSize))
    }

    private fun extractIndexAndPageSize(path: String): Pair<Int, Int>? {
        val regex = "/products/(\\d+)(?:/(\\d+))?".toRegex()
        val matchResult = regex.matchEntire(path) ?: return null
        val index = matchResult.groupValues[1].toIntOrNull() ?: return null
        val pageSize = matchResult.groupValues.getOrNull(2)?.toIntOrNull() ?: DEFAULT_PAGE_SIZE
        return index to pageSize
    }

    private fun getProductSubList(
        startIndex: Int,
        pageSize: Int,
    ): List<ProductEntity> {
        val endIndex = (startIndex + pageSize).coerceAtMost(ProductEntity.STUB_LIST.size)
        return ProductEntity.STUB_LIST.subList(startIndex, endIndex)
    }

    private fun response(products: List<ProductEntity>): MockResponse {
        return try {
            val response = gson.toJson(products)
            MockResponse()
                .setResponseCode(HTTP_OK)
                .setHeader("Content-Type", "application/json".toMediaType())
                .setBody(response)
        } catch (e: Exception) {
            MockResponse().setResponseCode(HTTP_NOT_FOUND)
        }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
