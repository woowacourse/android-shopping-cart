package woowacourse.shopping.data.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.network.entitiy.ProductEntity
import woowacourse.shopping.data.network.entitiy.ProductPageEntity
import woowacourse.shopping.data.storage.ProductStorage

class MockingServer : ProductService {
    private val mockWebServer = MockWebServer()
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun getProduct(productId: Long): ProductEntity {
        val product = ProductStorage[productId]
        val json = gson.toJson(product)
        val path = "$BASE_PATH$productId"

        enqueueResponse(json)
        val responseBody = executeRequest(path)
        return parseProduct(responseBody)
    }

    override fun getProducts(productIds: List<Long>): List<ProductEntity> {
        val products = ProductStorage.getProductsById(productIds)

        val json = gson.toJson(products)
        val path = "$BASE_PATH$productIds"

        enqueueResponse(json)
        val responseBody = executeRequest(path)
        return parseProductList(responseBody)
    }

    override fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageEntity {
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

    private fun parseProduct(json: String) = gson.fromJson(json, ProductEntity::class.java)

    private fun parseProductSinglePage(json: String) = gson.fromJson(json, ProductPageEntity::class.java)

    private fun parseProductList(json: String): List<ProductEntity> {
        val type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    companion object {
        private const val BASE_PATH = "/products/"
        private const val RESPONSE_SUCCESS = 200
    }
}
