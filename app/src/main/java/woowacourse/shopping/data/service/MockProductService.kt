package woowacourse.shopping.data.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.mockserver.MockServer

class MockProductService(
    private val client: OkHttpClient,
    private val server: MockServer,
) : ProductService {
    private val gson = Gson()

    override fun findProductById(id: Long): Product {
        val url = "/product/$id"
        return executeRequest(url, object : TypeToken<Product>() {})
    }

    override fun findProductsByIds(ids: List<Long>): List<Product> {
        val url = "/product?ids=${gson.toJson(ids)}"
        return executeRequest(url, object : TypeToken<List<Product>>() {})
    }

    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): List<Product> {
        val url = "/products/?offset=$offset&limit=$limit"
        return executeRequest(url, object : TypeToken<List<Product>>() {})
    }

    override fun calculateHasMore(
        offset: Int,
        limit: Int,
    ): Boolean {
        val url = "/products/hasMore?offset=$offset&limit=$limit"
        return executeRequest(url, object : TypeToken<Boolean>() {})
    }

    private inline fun <reified T> executeRequest(
        url: String,
        typeToken: TypeToken<T>,
    ): T {
        val httpUrl = server.createUrlWithBase(url)
        val request = Request.Builder().url(httpUrl).build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw IllegalArgumentException(
                FAILURE_MESSAGE.format(response.code, httpUrl),
            )
        }

        val responseBody = response.body?.string()
        if (responseBody.isNullOrEmpty()) {
            throw IllegalStateException(
                EMPTY_RESPONSE_MESSAGE.format(httpUrl),
            )
        }

        return gson.fromJson(responseBody, typeToken.type)
    }

    companion object {
        private const val FAILURE_MESSAGE = "Request failed: HTTP %d for URL %s"
        private const val EMPTY_RESPONSE_MESSAGE = "Empty response body for URL %s"
    }
}
