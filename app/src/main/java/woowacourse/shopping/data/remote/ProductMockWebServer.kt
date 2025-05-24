package woowacourse.shopping.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.domain.model.Product

class ProductMockWebServer : ProductService {
    private val mockWebServer = MockWebServer()
    private val client: OkHttpClient = OkHttpClientProvider.provideClient()
    private val gson = Gson()

    override fun start() {
        mockWebServer.dispatcher = ProductMockWebServerDispatcher
        mockWebServer.start(PORT_NUMBER)
    }

    override fun fetchPagingProducts(
        offset: Int,
        pageSize: Int,
    ): List<Product> =
        executeRequest(
            PAGING_PRODUCTS.format(offset, pageSize),
            object : TypeToken<List<Product>>() {}.type,
        )

    override fun fetchProductById(id: Long): Product = executeRequest(FIND_PRODUCT.format(id), object : TypeToken<Product>() {}.type)

    override fun fetchProducts(): List<Product> = executeRequest(ALL_PRODUCTS, object : TypeToken<List<Product>>() {}.type)

    override fun shutdown() = mockWebServer.shutdown()

    private fun <T> executeRequest(
        url: String,
        typeToken: java.lang.reflect.Type,
    ): T {
        val request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            val body =
                response.body?.string() ?: throw IllegalStateException(ERROR_RESPONSE_BODY_NULL)
            return gson.fromJson(body, typeToken)
        }
    }

    companion object {
        private const val PORT_NUMBER = 1999
        private const val BASE_URL = "http://localhost:$PORT_NUMBER"
        private const val FIND_PRODUCT = "$BASE_URL/products/find/%d"
        private const val ALL_PRODUCTS = "$BASE_URL/products"
        private const val PAGING_PRODUCTS = "$BASE_URL/products/paging?offset=%d&pageSize=%d"
        private const val ERROR_RESPONSE_BODY_NULL = "Response body is null"
    }
}
