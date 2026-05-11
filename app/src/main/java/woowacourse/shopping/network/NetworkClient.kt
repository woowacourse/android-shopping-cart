package woowacourse.shopping.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class NetworkClient {
    private val client = OkHttpClient()

    suspend fun getProducts(baseUrl: String): String =
        withContext(Dispatchers.IO) {
            val request =
                Request
                    .Builder()
                    .url("${baseUrl}products")
                    .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("네트워크 오류: $response")
                response.body.string()
            }
        }
}
