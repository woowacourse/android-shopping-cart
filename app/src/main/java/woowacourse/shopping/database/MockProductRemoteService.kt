package woowacourse.shopping.database

import model.Name
import model.Price
import model.Product
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import java.io.IOException

class MockProductRemoteService {
    private var _mockWebServer: MockWebServer? = null
    private val mockWebServer: MockWebServer
        get() = _mockWebServer!!

    private var dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products?lastProductId=0" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(firstJsonProducts)
                }
                "/products?lastProductId=20" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(secondJsonProducts)
                }
                "/products?lastProductId=40" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(thirdJsonProducts)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    fun request(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit,
    ) {

        if (_mockWebServer == null) {
            _mockWebServer = MockWebServer()
            _mockWebServer?.url("/")
            _mockWebServer?.dispatcher = dispatcher
        }

        val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
        val okHttpClient = OkHttpClient()
        val url = "$baseUrl/products?lastProductId=$lastProductId"
        val request = Request.Builder().url(url).build()

        okHttpClient.run {
            newCall(request).enqueue(
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
                },
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
            val jsonPrice = jsonProduct.getInt("price")

            val price = Price(jsonPrice)
            val product = Product(id, Name(name), imageUrl, price)
            productList.add(product)
        }

        return productList
    }
}
