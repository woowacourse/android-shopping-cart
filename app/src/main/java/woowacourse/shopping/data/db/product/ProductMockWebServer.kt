package woowacourse.shopping.data.db.product

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.home.HomeViewModel

class ProductMockWebServer : ProductService {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson: Gson = Gson()

    override fun getSize(): Int {
        val size = ProductDatabase.products.size
        val body = Gson().toJson(size)
        val serverRequest = makeServerRequest(body, "size")
        val response = makeResponse(serverRequest)
        val responseBody = response.body?.string() ?: return 0
        return gson.fromJson(responseBody, Int::class.java)
    }

    override fun findPageProducts(
        start: Int,
        offset: Int,
    ): List<Product> {
        val products = ProductDatabase.products
        val page = offset / HomeViewModel.PAGE_SIZE

        val pageProducts = products.subList(start, offset)
        val body = Gson().toJson(pageProducts)
        val serverRequest = makeServerRequest(body, "${page}page")
        val response = makeResponse(serverRequest)
        val responseBody = response.body?.string() ?: return emptyList()
        val productType = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(responseBody, productType)
    }

    override fun findProductById(id: Long): Product {
        val product = ProductDatabase.products.first { it.id == id }
        val body = gson.toJson(product)
        val serverRequest = makeServerRequest(body, id.toString())
        val response = makeResponse(serverRequest)
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, Product::class.java)
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

    private fun openServer(
        server: MockWebServer,
        body: String,
    ) {
        server.enqueue(MockResponse().setBody(body).setResponseCode(200))
    }

    private fun makeServerUrl(
        server: MockWebServer,
        requestUrl: String,
    ): String {
        val serverUrl = server.url(MOCK_SERVER_PATH).toString()
        return "${serverUrl}$requestUrl"
    }

    private fun makeResponse(serverRequest: Request): Response {
        return client.newCall(serverRequest).execute()
    }

    companion object {
        private const val MOCK_SERVER_PATH = "/products"
    }
}
