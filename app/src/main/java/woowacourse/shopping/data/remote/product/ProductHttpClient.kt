package woowacourse.shopping.data.remote.product

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.remote.dto.ProductResponse

class ProductHttpClient(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val json: Json,
) {
    suspend fun getProducts(): List<ProductResponse> =
        withContext(Dispatchers.IO) {
            val request =
                Request
                    .Builder()
                    .url("${baseUrl}products")
                    .get()
                    .build()

            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    error("상품 목록 요청 실패: ${response.code}")
                }

                val responseBody = response.body?.string() ?: error("응답 body가 비어 있음")

                json.decodeFromString(
                    ListSerializer(ProductResponse.serializer()),
                    responseBody,
                )
            }
        }
}
