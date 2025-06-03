package woowacourse.shopping.data.service

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class ProductServiceImpl : ProductService {
    private val client = OkHttpClient()

    override fun getProductById(id: Long): Response {
        val url =
            BASE_URL
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("product")
                .addQueryParameter(PARAM_ID, id.toString())
                .build()
        return executeRequest(url)
    }

    override fun getProductsByIds(ids: List<Long>): Response {
        val url =
            BASE_URL
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
        for (id in ids) {
            url.addQueryParameter(PARAM_ID, id.toString())
        }
        return executeRequest(url.build())
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): Response {
        val url =
            BASE_URL
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addPathSegment("page")
                .addQueryParameter(PARAM_LIMIT, limit.toString())
                .addQueryParameter(PARAM_OFFSET, offset.toString())
                .build()
        return executeRequest(url)
    }

    private fun executeRequest(url: HttpUrl): Response {
        val request = Request.Builder().url(url).build()
        return client.newCall(request).execute()
    }

    companion object {
        private const val BASE_URL: String = "http://localhost:8888"

        private const val PARAM_ID: String = "id"
        private const val PARAM_LIMIT: String = "limit"
        private const val PARAM_OFFSET: String = "offset"
    }
}
