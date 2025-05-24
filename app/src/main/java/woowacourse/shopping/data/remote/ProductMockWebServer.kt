package woowacourse.shopping.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.domain.model.Product

class ProductMockWebServer : ProductService {
    private val mockWebServer = MockWebServer()
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun start() {
        mockWebServer.dispatcher = ProductMockWebServerDispatcher
        mockWebServer.start(PORT_NUMBER)
    }

    override fun fetchPagingProducts(
        offset: Int,
        pageSize: Int,
    ): List<Product> {
        val request =
            Request
                .Builder()
                .url(PAGING_PRODUCTS.format(offset, pageSize))
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }

    override fun fetchProductById(id: Long): Product {
        val request =
            Request
                .Builder()
                .url(FIND_PRODUCT.format(id))
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun fetchProducts(): List<Product> {
        val request =
            Request
                .Builder()
                .url(ALL_PRODUCTS)
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }

    override fun shutdown() {
        mockWebServer.shutdown()
    }

    companion object {
        private const val PORT_NUMBER = 1999
        private const val BASE_URL = "http://localhost:$PORT_NUMBER"
        private const val FIND_PRODUCT = "$BASE_URL/products/find/%d"
        private const val ALL_PRODUCTS = "$BASE_URL/products"
        private const val PAGING_PRODUCTS = "$BASE_URL/products/paging?offset=%d&pageSize=%d"
    }
}
