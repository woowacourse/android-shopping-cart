package woowacourse.shopping.data.product.server

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.product.server.ProductServerApiPath.BASE_PORT
import woowacourse.shopping.data.product.server.ProductServerApiPath.FIND_PRODUCT_PAGE_URL
import woowacourse.shopping.data.product.server.ProductServerApiPath.FIND_PRODUCT_URL
import woowacourse.shopping.data.product.server.ProductServerApiPath.TOTAL_COUNT_URL
import kotlin.concurrent.thread

class MockWebProductServer(private val dispatcher: Dispatcher) :
    ProductServer {
    private val server: MockWebServer = MockWebServer()
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson: Gson = Gson()

    override fun start() {
        thread {
            server.dispatcher = dispatcher
            server.start(BASE_PORT)
        }.join()
    }

    override fun findOrNull(id: Long): Product? {
        val request =
            Request.Builder()
                .url(FIND_PRODUCT_URL.format(id))
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val request =
            Request.Builder()
                .url(FIND_PRODUCT_PAGE_URL.format(page, pageSize))
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }

    override fun totalCount(): Int {
        val request =
            Request.Builder()
                .url(TOTAL_COUNT_URL)
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Int>() {}.type)
    }

    override fun shutDown() {
        thread {
            server.shutdown()
        }.join()
    }
}
