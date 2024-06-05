package woowacourse.shopping.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.local.dao.CartProductDao
import woowacourse.shopping.data.local.entity.CartProductEntity

class OkHttpDataSource(private val cartProductDao: CartProductDao) : RemoteDataSource {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    override fun findProductByPagingWithMock(
        offset: Int,
        pageSize: Int,
    ): List<CartProductEntity> {
        val products = cartProductDao.findProductByPaging(offset, pageSize)
        val body = gson.toJson(products)
        val serverRequest = makeServerRequest(body, "?offset=$offset&pageSize=$pageSize")
        val response: Response = makeResponse(serverRequest)
        val responseBody = response.body?.string() ?: return emptyList()
        val productType = object : TypeToken<List<CartProductEntity>>() {}.type
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
