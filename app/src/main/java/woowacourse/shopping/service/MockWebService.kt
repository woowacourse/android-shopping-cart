package woowacourse.shopping.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.DummyProductStore

class MockWebService {
    private val client = OkHttpClient()

    fun findProductById(productId: Int): Product {
        val product = DummyProductStore().findById(productId)
        val body = Gson().toJson(product)
        val serverRequest = makeServerRequest(body, productId.toString())
        val response: Response = client.newCall(serverRequest).execute()
        val responseBody = response.body?.string()
        return Gson().fromJson(responseBody, Product::class.java)
    }

    fun findPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product> {
        val pagingData = DummyProductStore().loadDataAsNeeded(offset)
        val body = Gson().toJson(pagingData)
        val serverRequest = makeServerRequest(body, "?offset=$offset&pagingSize=$pagingSize")
        val response: Response = client.newCall(serverRequest).execute()
        val responseBody = response.body?.string() ?: return emptyList()
        val productType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(responseBody, productType)
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
        private const val DEFAULT_URL = ""
        private const val MOCK_SERVER_PATH = "/products"
    }
}
