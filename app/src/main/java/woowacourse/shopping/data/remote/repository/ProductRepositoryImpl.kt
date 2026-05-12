package woowacourse.shopping.data.remote.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import woowacourse.shopping.data.remote.dto.WebServerResponse
import woowacourse.shopping.data.remote.dto.toObject
import woowacourse.shopping.domain.Product

class ProductRepositoryImpl(
    private val client: OkHttpClient,
    private val baseUrl: String,
) : ProductRepository {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> =
        withContext(Dispatchers.IO) {
            val offset = page * pageSize
            val url = "${baseUrl}products?offset=$offset&limit=$pageSize"

            val request = Request.Builder().url(url).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseBody = response.body.string() ?: ""
                val webResponse = json.decodeFromString<List<WebServerResponse>>(responseBody)
                webResponse.map { it.toObject() }
            }
        }

    override suspend fun getProduct(id: String): Product =
        withContext(Dispatchers.IO) {
            val url = "${baseUrl}products/$id"

            val request = Request.Builder().url(url).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseBody = response.body.string() ?: ""
                val webResponse = json.decodeFromString<WebServerResponse>(responseBody)
                webResponse.toObject()
            }
        }
}
