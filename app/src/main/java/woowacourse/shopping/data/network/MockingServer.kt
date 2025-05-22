package woowacourse.shopping.data.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.network.dto.ProductDto
import woowacourse.shopping.data.network.dto.ProductPageDto
import woowacourse.shopping.data.storage.ProductStorage

class MockingServer : ProductService {
    private val mockWebServer = MockWebServer()
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun getProduct(productId: Long): ProductDto {
        val product = ProductStorage[productId]
        val json = gson.toJson(product)
        val path = "$BASE_PATH$productId"

        enqueueResponse(json)
        val responseBody = executeRequest(path)
        return parseProduct(responseBody)
    }

    override fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageDto {
        val page = ProductStorage.singlePage(fromIndex, toIndex)
        val json = gson.toJson(page)
        val path = "$BASE_PATH?page=$fromIndex-$toIndex"

        enqueueResponse(json)
        val responseBody = executeRequest(path)
        return parseProductSinglePage(responseBody)
    }

    private fun enqueueResponse(body: String) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(RESPONSE_SUCCESS)
                .setBody(body),
        )
    }

    private fun executeRequest(path: String): String {
        val url = mockWebServer.url(path)
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()

        return response.body?.string()
            ?: throw IllegalStateException("MockWebServer returned no body")
    }

    private fun parseProduct(json: String) = gson.fromJson(json, ProductDto::class.java)

    private fun parseProductSinglePage(json: String) = gson.fromJson(json, ProductPageDto::class.java)

    companion object {
        private const val BASE_PATH = "/products/"
        private const val RESPONSE_SUCCESS = 200
    }
}
