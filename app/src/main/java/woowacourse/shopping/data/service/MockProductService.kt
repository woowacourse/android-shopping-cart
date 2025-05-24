package woowacourse.shopping.data.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.model.PageableResponse
import woowacourse.shopping.data.model.ProductResponse
import woowacourse.shopping.mockserver.MockServer

class MockProductService(
    private val client: OkHttpClient,
    private val server: MockServer,
) : ProductService {
    private val gson = Gson()

    override fun findProductById(id: Long): ProductResponse {
        val url = "/product/$id"
        return executeRequest(url, object : TypeToken<ProductResponse>() {})
    }

    override fun findProductsByIds(ids: List<Long>): List<ProductResponse> {
        val url = "/product?ids=${gson.toJson(ids)}"
        return executeRequest(url, object : TypeToken<List<ProductResponse>>() {})
    }

    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): PageableResponse<ProductResponse> {
        val url = "/products/?offset=$offset&limit=$limit"
        return executeRequest(url, object : TypeToken<PageableResponse<ProductResponse>>() {})
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
