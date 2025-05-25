package woowacourse.shopping.data.product.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.model.Product
import java.io.IOException

class ProductServiceImpl : ProductService {
    private val server = MockWebServer()
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun getProductById(id: Long): Product? {
        val product = ProductData.getProductById(id) ?: return null
        val jsonBody = gson.toJson(product)
        val response = enqueueAndExecute(jsonBody, "/$id")
        val responseBody = response.body?.string() ?: throw IOException()
        return gson.fromJson(responseBody, Product::class.java)
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        val pagedResult = ProductData.getPagedProducts(limit, offset)
        val jsonBody = gson.toJson(pagedResult)
        val response = enqueueAndExecute(jsonBody, "?limit=$limit&offset=$offset")
        val responseBody = response.body?.string() ?: throw IOException()
        val type = object : TypeToken<PagedResult<Product>>() {}.type
        return gson.fromJson(responseBody, type)
    }

    private fun enqueueAndExecute(
        jsonBody: String,
        path: String,
    ): Response {
        server.enqueue(MockResponse().setBody(jsonBody).setResponseCode(SUCCESS_RESPONSE_CODE))
        val url = server.url("$PATH$path").toString()
        val request = Request.Builder().url(url).build()
        return client.newCall(request).execute()
    }

    companion object {
        private const val PATH = "/products"
        private const val SUCCESS_RESPONSE_CODE = 200
    }
}
