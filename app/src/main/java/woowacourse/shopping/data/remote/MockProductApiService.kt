package woowacourse.shopping.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.domain.model.Product

class MockProductApiService(private val server: MockWebServer) : ProductApiService {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun loadById(productId: Long): Product {
        val request =
            Request.Builder()
                .url("${server.url("/products")}/$productId")
                .build()
        val response: Response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: throw NoSuchElementException()
        return gson.fromJson(responseBody, Product::class.java)
    }

    override fun load(
        startPage: Int,
        pageSize: Int,
    ): List<Product> {
        val request =
            Request.Builder()
                .url("${server.url("/products")}?startPage=$startPage&pageSize=$pageSize")
                .build()
        val response: Response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return emptyList()
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }
}
