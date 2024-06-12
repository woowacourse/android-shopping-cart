package woowacourse.shopping.data.shopping.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.domain.Product

class OkHttpClientApi() : ProductApi {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun findById(id: Int): Result<Product> {
        val request = Request.Builder().url("$BASE_URL/products/$id").build()
        return runCatching {
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: error(PRODUCT_ERROR)
            gson.fromJson(body, Product::class.java)
        }
    }

    override fun findByOffset(
        offset: Int,
        size: Int,
    ): Result<List<Product>> {
        val request = Request.Builder().url("$BASE_URL/paging-product/$size/$offset").build()
        return runCatching {
            val response = client.newCall(request).execute()
            val gson = Gson()
            val type = object : TypeToken<List<Product>>() {}.type
            val body =
                response.body?.string() ?: error(
                    PAGING_PRODUCT_ERROR,
                )
            gson.fromJson(body, type)
        }
    }

    companion object {
        const val BASE_URL = "http://localhost:12345"
        const val PRODUCT_ERROR = "product를 찾지 못했습니다."
        const val PAGING_PRODUCT_ERROR = "paging product를 찾지 못했습니다."
    }
}
