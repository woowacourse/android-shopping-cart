package woowacourse.shopping.data.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.api.ApiClient.BASE_PORT
import woowacourse.shopping.data.api.ApiClient.GET_FIND_PRODUCT
import woowacourse.shopping.data.api.ApiClient.GET_PAGING_PRODUCT
import woowacourse.shopping.data.model.remote.ProductEntity
import kotlin.concurrent.thread

class NetworkModule : ApiService {
    private val server =
        MockWebServer().apply {
            thread {
                dispatcher = NetworkDispatcher
                start(BASE_PORT)
            }
        }
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun findProductById(id: Long): Result<ProductEntity> =
        runCatching {
            val request =
                Request.Builder()
                    .url(GET_FIND_PRODUCT.format(id))
                    .build()
            val response: Response = client.newCall(request).execute()
            val body = response.body?.string()

            gson.fromJson(body, object : TypeToken<ProductEntity>() {}.type)
        }

    override fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductEntity>> =
        runCatching {
            val request =
                Request.Builder()
                    .url(GET_PAGING_PRODUCT.format(page, pageSize))
                    .build()
            val response: Response = client.newCall(request).execute()
            val body = response.body?.string()

            gson.fromJson(body, object : TypeToken<List<ProductEntity>>() {}.type)
        }

    override fun shutdown(): Result<Unit> =
        runCatching {
            client.dispatcher.executorService.shutdown()
            client.connectionPool.evictAll()
            server.shutdown()
        }
}
