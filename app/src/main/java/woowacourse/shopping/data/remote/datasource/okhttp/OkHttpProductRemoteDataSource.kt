package woowacourse.shopping.data.remote.datasource.okhttp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.remote.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.remote.dto.ProductResponse
import woowacourse.shopping.data.remote.exception.NetworkException

class OkHttpProductRemoteDataSource(
    private val client: OkHttpClient,
    private val baseUrl: String,
    private val json: Json = Json { ignoreUnknownKeys = true },
) : ProductRemoteDataSource {
    override suspend fun getProducts(): List<ProductResponse> =
        withContext(Dispatchers.IO) {
            val request =
                Request
                    .Builder()
                    .url(
                        baseUrl
                            .toHttpUrl()
                            .newBuilder()
                            .addPathSegment("products")
                            .build(),
                    ).get()
                    .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("HTTP Error ${response.code}")
                val body = checkNotNull(response.body) { "Response body is null" }
                json.decodeFromString(body.string())
            }
        }

    override suspend fun getProduct(productId: Int): ProductResponse =
        withContext(Dispatchers.IO) {
            val request =
                Request
                    .Builder()
                    .url(
                        baseUrl
                            .toHttpUrl()
                            .newBuilder()
                            .addPathSegment("products")
                            .addPathSegment(productId.toString())
                            .build(),
                    ).get()
                    .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw NetworkException(
                        code = response.code,
                        message = "서버 오류가 발생했습니다. (에러 코드: ${response.code})",
                    )
                }
                val body = checkNotNull(response.body) { "Response body is null" }
                json.decodeFromString(body.string())
            }
        }
}
