package woowacourse.shopping.data.service

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.Product

class ProductServiceImpl : ProductService {
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun getProductById(id: Long): Product? {
        val url =
            BASE_URL
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("product")
                .addQueryParameter(PARAM_ID, id.toString())
                .build()

        val body = executeRequest(url)
        return try {
            gson.fromJson(body, Product::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    override fun getProductsByIds(ids: List<Long>): List<Product>? {
        val url =
            BASE_URL
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("products")

        for (id in ids) {
            url.addQueryParameter(PARAM_ID, id.toString())
        }

        val body = executeRequest(url.build())
        return try {
            val type = object : TypeToken<List<Product>>() {}.type
            gson.fromJson<List<Product>>(body, type)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product>? {
        val url =
            BASE_URL
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addPathSegment("page")
                .addQueryParameter(PARAM_LIMIT, limit.toString())
                .addQueryParameter(PARAM_OFFSET, offset.toString())
                .build()

        val body = executeRequest(url)
        return try {
            val type = object : TypeToken<PagedResult<Product>>() {}.type
            gson.fromJson<PagedResult<Product>>(body, type)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    private fun executeRequest(url: okhttp3.HttpUrl): String? {
        val request = Request.Builder().url(url).build()
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    null
                } else {
                    response.body?.string()?.trim()
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val BASE_URL: String = "http://localhost:8888"

        private const val PARAM_ID: String = "id"
        private const val PARAM_LIMIT: String = "limit"
        private const val PARAM_OFFSET: String = "offset"
    }
}
