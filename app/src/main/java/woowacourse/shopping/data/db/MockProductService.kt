package woowacourse.shopping.data.db

import com.shopping.domain.Product
import okhttp3.*
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import woowacourse.shopping.data.repository.mockProducts
import java.io.IOException

class MockProductService {
    private var _mockWebServer: MockWebServer? = null
    private val mockWebServer: MockWebServer
        get() = _mockWebServer!!

    private val dispatcher: Dispatcher

    init {
        dispatcher = object: Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    "/products" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(mockProducts)
                    }
                    else -> MockResponse().setResponseCode(404)
                }
            }

        }
    }

    fun request(
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        synchronized(this) {
            if (_mockWebServer == null) {
                _mockWebServer = MockWebServer()
                _mockWebServer?.url("/")
                _mockWebServer?.dispatcher = dispatcher
            }

            val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
            val okHttpClient = OkHttpClient()
            val url = "$baseUrl/products"
            val request = Request.Builder().url(url).build()

            okHttpClient.newCall(request).enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.code >= 400) return onFailure()
                        val responseBody = response.body?.string()
                        response.close()

                        val result = responseBody?.let {
                            parseJsonToProductList(it)
                        } ?: emptyList()

                        onSuccess(result)
                    }
                }
            )
        }
    }

    private fun parseJsonToProductList(responseString: String): List<Product> {
        val productList = mutableListOf<Product>()

        val jsonArray = JSONArray(responseString)
        for (i in 0 until jsonArray.length()) {
            val jsonProduct = jsonArray.getJSONObject(i)
            val id = jsonProduct.getInt("id")
            val name = jsonProduct.getString("name")
            val imageUrl = jsonProduct.getString("imageUrl")
            val price = jsonProduct.getInt("price")

            val product = Product(id, name, imageUrl, price)
            productList.add(product)
        }

        return productList
    }

}