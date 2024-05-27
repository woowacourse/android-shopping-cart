package woowacourse.shopping.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.remote.ProductMockWebServer.Companion.BASE_PORT
import woowacourse.shopping.remote.ProductMockWebServer.Companion.GET_PAGING_PRODUCTS
import woowacourse.shopping.remote.ProductMockWebServer.Companion.GET_PRODUCT
import woowacourse.shopping.remote.ProductMockWebServer.Companion.GET_TOTAL_COUNT
import kotlin.concurrent.thread

class MockProductApiService : ProductApiService {
    private val server =
        MockWebServer().apply {
            thread {
                dispatcher = NetworkDispatcher()
                start(BASE_PORT)
            }
        }

    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun loadById(id: Long): ProductData {
        val request = Request.Builder().url(GET_PRODUCT.format(id)).build()
        println("request: $request")

        val response: Response = client.newCall(request).execute()
        println("response: $response")
        val responseBody = response.body?.string() ?: throw NoSuchElementException()
        println("responseBody: $responseBody")
        return gson.fromJson(responseBody, object : TypeToken<ProductData>() {}.type)
    }

    override fun loadPaged(page: Int): List<ProductData> {
        val request = Request.Builder().url(GET_PAGING_PRODUCTS.format(page)).build()
        println("request: $request")

        val response: Response = client.newCall(request).execute()
        println("response: $response")

        val responseBody = response.body?.string() ?: return emptyList()
        println("responseBody: $responseBody")

        return gson.fromJson(responseBody, object : TypeToken<List<ProductData>>() {}.type)
    }

    override fun count(): Int {
        val request = Request.Builder().url(GET_TOTAL_COUNT).build()
        println("request: $request")

        val response: Response = client.newCall(request).execute()
        println("response: $response")

        val responseBody = response.body?.string() ?: return 0
        println("responseBody: $responseBody")

        return gson.fromJson(responseBody, Int::class.java)
    }

    override fun shutDown(): Boolean {
        client.dispatcher.executorService.shutdown()
        client.connectionPool.evictAll()
        server.shutdown() // io exception 발생 가능성 있음
        return true
    }

    companion object {
        private const val TAG = "MockProductApiService"
    }
}
