package woowacourse.shopping.data.source.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.remote.model.ProductResponse
import java.io.IOException

class ProductRemoteDataSourceImpl(
    private val client: OkHttpClient,
    private val baseUrl: String,
) : ProductDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getProducts(
        startIndex: Int,
        count: Int,
    ): List<ProductResponse> =
        withContext(Dispatchers.IO) {
            val url = "${baseUrl}products?start=$startIndex&count=$count"
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("상품 목록 로드 실패: ${response.code}")
                val body = response.body?.string() ?: ""
                json.decodeFromString<List<ProductResponse>>(body)
            }
        }

    override suspend fun getProductById(id: String): ProductResponse =
        withContext(Dispatchers.IO) {
            val url = "${baseUrl}products/$id"
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("상품 상세 로드 실패 (ID: $id): ${response.code}")
                val body = response.body?.string() ?: ""
                json.decodeFromString<ProductResponse>(body)
            }
        }

    override suspend fun getTotalCount(): Int =
        withContext(Dispatchers.IO) {
            val url = "${baseUrl}products/count"
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("전체 개수 조회 실패: ${response.code}")
                response.body?.string()?.toInt() ?: 0
            }
        }
}
