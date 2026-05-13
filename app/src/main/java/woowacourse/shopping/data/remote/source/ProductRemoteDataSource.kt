package woowacourse.shopping.data.remote.source

import com.google.gson.Gson
import kotlin.jvm.java
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import woowacourse.shopping.data.NetworkConfig
import woowacourse.shopping.data.remote.api.ShoppingMockServer
import woowacourse.shopping.data.remote.model.ProductResponse

class ProductRemoteDataSource {
    private val client = NetworkConfig.okHttpClient
    private val gson = Gson()

    suspend fun getProducts(): Result<List<ProductResponse>> = withContext(Dispatchers.IO) {
        runCatching {
            val request = Request.Builder()
                .url("${ShoppingMockServer.BASE_URL}products")
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful.not()) throw Exception("상품 목록 로드 실패: $response")

                val body = response.body.string()
                gson.fromJson(body, Array<ProductResponse>::class.java).toList()
            }
        }
    }

    suspend fun getProduct(id: String): ProductResponse = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("${ShoppingMockServer.BASE_URL}products/$id")
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful.not()) throw Exception("상품 로드 실패: $response")

            val body = response.body.string()
            gson.fromJson(body, ProductResponse::class.java)
        }
    }
}
