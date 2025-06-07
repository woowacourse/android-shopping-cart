package woowacourse.shopping.data.remote

import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class RemoteInventoryDataSourceImpl(
    private val client: OkHttpClient,
    private val baseUrl: HttpUrl,
) : RemoteInventoryDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    override fun fetchProduct(id: Int): ProductResponse {
        val request =
            Request.Builder()
                .url(baseUrl.newBuilder().addPathSegment(id.toString()).build())
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("IOException: ${response.code}")
            }
            val jsonStr = response.body?.string() ?: throw IOException("Empty response body")
            return json.decodeFromString(jsonStr)
        }
    }

    override fun fetchAllProducts(): ProductsResponse {
        val request =
            Request.Builder()
                .url(baseUrl.newBuilder().build())
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("IOException: ${response.code}")
            }
            val jsonStr = response.body?.string() ?: throw IOException("Empty response body")
            return json.decodeFromString(jsonStr)
        }
    }
}
