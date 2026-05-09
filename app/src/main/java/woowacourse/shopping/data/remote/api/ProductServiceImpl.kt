package woowacourse.shopping.data.remote.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ProductServiceImpl(
    private val client: OkHttpClient,
    private val baseUrlProvider: () -> String,
) : ProductService {

    override suspend fun getProducts(): String =
        get("products")

    override suspend fun getProduct(id: String): String =
        get("products/$id")

    private suspend fun get(path: String): String = withContext(Dispatchers.IO) {
        val url = "${baseUrlProvider()}$path"
        val request = Request.Builder().url(url).get().build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("HTTP ${response.code}: ${response.message}")
            }
            response.body?.string()
                ?: throw IOException("Empty response body")
        }
    }
}
