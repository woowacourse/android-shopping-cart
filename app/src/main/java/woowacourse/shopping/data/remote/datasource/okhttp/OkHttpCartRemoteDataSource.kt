package woowacourse.shopping.data.remote.datasource.okhttp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.remote.datasource.CartRemoteDataSource
import woowacourse.shopping.data.remote.dto.CartItemResponse

class OkHttpCartRemoteDataSource(
    private val client: OkHttpClient,
    private val baseUrl: String,
    private val json: Json = Json { ignoreUnknownKeys = true },
) : CartRemoteDataSource {
    override suspend fun getCartItems(): List<CartItemResponse> =
        withContext(Dispatchers.IO) {
            val request =
                Request
                    .Builder()
                    .url(
                        baseUrl
                            .toHttpUrl()
                            .newBuilder()
                            .addPathSegment("cart-items")
                            .build(),
                    ).get()
                    .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("HTTP Error ${response.code}")
                json.decodeFromString(response.body.string())
            }
        }
}
