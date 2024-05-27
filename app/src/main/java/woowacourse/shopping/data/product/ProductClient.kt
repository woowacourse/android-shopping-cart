package woowacourse.shopping.data.product

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.model.Product
import kotlin.concurrent.thread

class ProductClient : ProductService {
    private val mockWebServer = MockWebServer()
    private val client = OkHttpClient()
    private val gson = Gson()

    init {
        thread {
            mockWebServer.dispatcher = ProductDispatcher
        }.join()
    }

    override fun find(id: Long): Product {
        val request =
            Request.Builder()
                .url(mockWebServer.url("$PRODUCT_FIND_PATH$id"))
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun findAll(): List<Product> {
        val request =
            Request.Builder()
                .url(mockWebServer.url(PRODUCT_FIND_ALL_PATH))
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val request =
            Request.Builder()
                .url(mockWebServer.url(PRODUCT_FIND_RANGE_PATH))
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("hye", responseBody.toString())
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }

    override fun shutdown() {
        thread {
            mockWebServer.shutdown()
        }.join()
    }
}
