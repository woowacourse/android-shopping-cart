package woowacourse.shopping.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.dummy.DummyProductRepository
import woowacourse.shopping.domain.Product

class MockProductApiService() : ProductApiService {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun loadById(productId: Long): Product {
        val product = DummyProductRepository().products.find { it.id == productId }
        val body = gson.toJson(product)
        val serverRequest = makeServerRequest(body, productId.toString())
        val response: Response = makeResponse(serverRequest)
        val responseBody = response.body?.string() ?: throw NoSuchElementException()
        return gson.fromJson(responseBody, Product::class.java)
    }

    override fun load(
        startPage: Int,
        pageSize: Int,
    ): List<Product> {
        val startIndex = startPage * pageSize
        val products = DummyProductRepository().products
        val pagedProducts = products.drop(startIndex).take(pageSize)
        if (pagedProducts.isEmpty()) throw NoSuchElementException("상품 데이터를 모두 불러왔습니다")
        val body = gson.toJson(pagedProducts)
        val serverRequest = makeServerRequest(body, "?startIdx=$startIndex&pageSize=$pageSize")
        val response: Response = makeResponse(serverRequest)
        val responseBody = response.body?.string() ?: return emptyList()
        val productType = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(responseBody, productType)
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
        val serverUrl = server.url("/products").toString()
        return "${serverUrl}$requestUrl"
    }

    private fun makeResponse(serverRequest: Request): Response {
        return client.newCall(serverRequest).execute()
    }
}
