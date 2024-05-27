package woowacourse.shopping.data.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.model.Product

class ProductMockWebServer : ProductServerApi {
    private val mockWebServer = MockWebServer()
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun start() {
        mockWebServer.dispatcher = ProductMockWebServerDispatcher
        mockWebServer.start(12345)
    }

    override fun find(id: Long): Product {
        val request =
            Request.Builder()
                .url("http://localhost:12345/products/find/%d".format(id))
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun findAll(): List<Product> {
        val request =
            Request.Builder()
                .url("http://localhost:12345/products")
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }

    override fun getProducts(): List<Product> {
        val request =
            Request.Builder()
                .url("http://localhost:12345/products/paging")
                .build()

        val response = client.newCall(request).execute()

        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }

    override fun shutdown() {
        mockWebServer.shutdown()
    }
}
