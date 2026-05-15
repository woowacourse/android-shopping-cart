package woowacourse.shopping.data.source.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class ProductRemoteDataSource(
    private val httpClient: OkHttpClient,
    private val baseUrl: String,
) {
    suspend fun getProducts(): List<ProductEntity> =
        withContext(Dispatchers.IO) {
            val request =
                Request
                    .Builder()
                    .url("${baseUrl}products")
                    .build()

            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@use emptyList()
                }

                val responseBody = response.body?.string() ?: return@use emptyList()

                return@use Json.decodeFromString(ListSerializer(ProductEntity.serializer()), responseBody)
            }
        }
}
