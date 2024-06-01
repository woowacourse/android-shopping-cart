package woowacourse.shopping.data.product.mockserver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.utils.NoSuchDataException
import java.lang.reflect.Type

class MockProductService {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val baseUrl = "http://localhost:${MockServer.mockWebServer.port}"

    fun requestProduct(productId: Long): Product {
        val request =
            Request.Builder()
                .url("$baseUrl/products/$productId")
                .build()

        client.newCall(request).execute().use { response ->
            if (response.code == 404) throw NoSuchDataException("Product not found")
            if (!response.isSuccessful) throw Exception("Unexpected code $response")
            val responseBody = response.body?.string() ?: throw Exception("Response body is null")
            return gson.fromJson(responseBody, Product::class.java)
        }
    }

    fun requestProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product> {
        val request =
            Request.Builder()
                .url("$baseUrl/products/page/$offset/$pagingSize")
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")
            val responseBody = response.body?.string() ?: throw Exception("Response body is null")
            val listType: Type = object : TypeToken<List<Product>>() {}.type
            return gson.fromJson(responseBody, listType)
        }
    }

    fun requestProductCount(): Int {
        val request =
            Request.Builder()
                .url("$baseUrl/products/count")
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")
            val responseBody = response.body?.string() ?: throw Exception("Response body is null")
            return gson.fromJson(responseBody, Int::class.java)
        }
    }
}
