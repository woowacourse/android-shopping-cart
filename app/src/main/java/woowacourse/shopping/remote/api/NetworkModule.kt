package woowacourse.shopping.remote.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.remote.api.ApiClient.BASE_PORT
import woowacourse.shopping.remote.api.ApiClient.GET_FIND_PRODUCT
import woowacourse.shopping.remote.api.ApiClient.GET_PAGING_PRODUCT
import woowacourse.shopping.remote.model.ProductResponse
import kotlin.concurrent.thread

class NetworkModule : ApiService {
    private val server =
        MockWebServer().apply {
            thread {
                dispatcher = NetworkDispatcher()
                start(BASE_PORT)
            }
        }
    private val client: OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpExceptionInterceptor())
            .build()
    private val gson = Gson()

    override fun findProductById(id: Long): Result<ProductResponse> =
        runCatching {
            val request =
                Request.Builder()
                    .url(GET_FIND_PRODUCT.format(id))
                    .build()
            val response: Response = client.newCall(request).execute()
            val body = response.body?.string()

            gson.fromJson(body, object : TypeToken<ProductResponse>() {}.type)
        }

    override fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductResponse>> =
        runCatching {
            val request =
                Request.Builder()
                    .url(GET_PAGING_PRODUCT.format(page, pageSize))
                    .build()
            val response: Response = client.newCall(request).execute()
            val body = response.body?.string()

            gson.fromJson(body, object : TypeToken<List<ProductResponse>>() {}.type)
        }

    override fun shutdown(): Result<Unit> =
        runCatching {
            client.dispatcher.executorService.shutdown()
            client.connectionPool.evictAll()
            server.shutdown()
        }
}
