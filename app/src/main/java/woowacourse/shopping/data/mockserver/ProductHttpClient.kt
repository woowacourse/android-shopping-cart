package woowacourse.shopping.data.mockserver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.domain.model.Product
import java.io.IOException

class ProductHttpClient(
    private val baseUrl: String,
    private val client: OkHttpClient = OkHttpClient(),
    private val gson: Gson = Gson(),
) {
    fun getProductList(): List<Product> {
        val request =
            Request
                .Builder()
                .url("$baseUrl/products")
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("HTTP error ${response.code}")
            }
            val body = response.body?.string() ?: return emptyList()
            val type = object : TypeToken<List<Product>>() {}.type
            return gson.fromJson(body, type)
        }
    }
}
