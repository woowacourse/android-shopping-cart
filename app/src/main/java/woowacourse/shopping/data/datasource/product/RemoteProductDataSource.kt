package woowacourse.shopping.data.datasource.product

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.local.PRODUCT_DATA
import woowacourse.shopping.data.model.Product
import kotlin.concurrent.thread
import kotlin.math.min

class RemoteProductDataSource : ProductDataSource {
    private val client = OkHttpClient()
    private val products: List<Product> = PRODUCT_DATA

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, products.size)

        if (fromIndex > toIndex) return emptyList()

        var pagingData = products.subList(fromIndex, toIndex)

        thread {
            val body = Gson().toJson(pagingData)
            val serverRequest = makeServerRequest(body, "?offset=$page&pageSize=$pageSize")
            val response: Response = client.newCall(serverRequest).execute()
            val responseBody = response.body?.string()
            val productType = object : TypeToken<List<Product>>() {}.type
            pagingData = Gson().fromJson(responseBody, productType)
        }.join()

        return pagingData
    }

    override fun getProductById(id: Long): Product {
        var product: Product = products.first { it.id == id }
        thread {
            val body = Gson().toJson(product)
            val serverRequest = makeServerRequest(body, id.toString())
            val response: Response = client.newCall(serverRequest).execute()
            val responseBody = response.body?.string()
            product = Gson().fromJson(responseBody, Product::class.java)
        }.join()

        return product
    }

    override fun getProductByIds(ids: List<Long>): List<Product> {
        var products = ids.map { id -> products.first { it.id == id } }

        thread {
            val body = Gson().toJson(products)
            val serverRequest = makeServerRequest(body, ids.toString())
            val response: Response = client.newCall(serverRequest).execute()
            val responseBody = response.body?.string()
            val productType = object : TypeToken<List<Product>>() {}.type
            products = Gson().fromJson(responseBody, productType)
        }.join()

        return products
    }

    private fun makeServerRequest(
        body: String,
        requestUrl: String,
    ): Request {
        val server = MockWebServer()
        openServer(server, body)
        return Request.Builder()
            .url(makeServerUrl(server, requestUrl))
            .build()
    }

    private fun makeServerUrl(
        server: MockWebServer,
        requestUrl: String,
    ): String {
        val serverUrl = server.url(MOCK_SERVER_PATH).toString()
        return "${serverUrl}$requestUrl"
    }

    private fun openServer(
        server: MockWebServer,
        body: String,
    ) {
        server.enqueue(MockResponse().setBody(body).setResponseCode(200))
    }

    private fun makeResponse(serverRequest: Request): Response {
        return client.newCall(serverRequest).execute()
    }

    companion object {
        private const val DEFAULT_URL = ""
        private const val MOCK_SERVER_PATH = "/products"
    }
}
