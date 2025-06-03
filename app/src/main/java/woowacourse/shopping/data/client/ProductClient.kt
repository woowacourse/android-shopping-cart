package woowacourse.shopping.data.client

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.dto.ProductDto

class ProductClient(private val baseUrl: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun fetchProducts(page: Int, limit: Int): List<ProductDto> {
        val url = "$baseUrl/products?page=$page&limit=$limit"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("서버 에러: ${response.code}")

            val body = response.body?.string() ?: throw Exception("응답 본문 없음")
            val type = object : TypeToken<List<ProductDto>>() {}.type

            return gson.fromJson(body, type)
        }
    }
}