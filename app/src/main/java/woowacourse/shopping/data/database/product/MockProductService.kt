package woowacourse.shopping.data.database.product

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.service.ProductService

class MockProductService : ProductService {
    private val client = OkHttpClient()
    private val productsJson: String = Gson().toJson(ProductDatabase.products)

    override fun fetchProductsSize(): Int {
        val size = ProductDatabase.products.size
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody(Gson().toJson(size)).setResponseCode(200))
        val serverUrl = server.url("/").toString()
        val request =
            Request.Builder()
                .url("$serverUrl/products/size")
                .build()

        val response: Response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return 0
        return Gson().fromJson(responseBody, Int::class.java)
    }

    override fun findAll(): List<Product> {
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody(productsJson).setResponseCode(200))
        val serverUrl = server.url("/").toString()
        val request =
            Request.Builder()
                .url("$serverUrl/$MOCK_SERVER_URL")
                .build()
        val response: Response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return emptyList()
        val productType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(responseBody, productType)
    }

    override fun findProductById(productId: Long): Product? {
        val product = ProductDatabase.products.find { it.id == productId }
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody(Gson().toJson(product)).setResponseCode(200))
        val serverUrl = server.url("/").toString()
        val request =
            Request.Builder()
                .url("$serverUrl/products/$productId")
                .build()

        val response: Response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return null
        return Gson().fromJson(responseBody, Product::class.java)
    }

    override fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product> {
        val server = MockWebServer()
        val pagingData =
            if (offset >= ProductDatabase.products.size) {
                emptyList()
            } else {
                ProductDatabase.products.drop(offset)
            }.take(pagingSize)

        server.enqueue(MockResponse().setBody(Gson().toJson(pagingData)).setResponseCode(200))
        val serverUrl = server.url("/").toString()
        val request =
            Request.Builder()
                .url("$serverUrl/products?offset=$offset&pagingSize=$pagingSize")
                .build()

        val response: Response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return emptyList()
        val productType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(responseBody, productType)
    }

    companion object {
        private const val MOCK_SERVER_URL = "/products"
    }
}
