package woowacourse.shopping.data

import com.example.domain.datasource.firstPageProducts
import com.example.domain.datasource.secondPageProducts
import com.example.domain.datasource.thirdPageProducts
import com.example.domain.model.Price
import com.example.domain.model.Product
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONObject
import java.io.IOException

class ProductMockWebServer {
    private lateinit var mockWebServer: MockWebServer
    private val okHttpClient = OkHttpClient()

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products?page=1" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(firstPageProducts)
                }

                "/products?page=2" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(secondPageProducts)
                }

                "/products?page=3" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(thirdPageProducts)
                }

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    fun request(
        page: Int = 1,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        synchronized(this) {
            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcher
            mockWebServer.url("/")

            val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
            val url = "$baseUrl/products?page=$page"
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
                            parseJsonToProducts(responseBody)
                        } ?: emptyList()

                        onSuccess(result)
                    }
                }
            )
        }
    }

    private fun parseJsonToProducts(json: String): List<Product> {
        val products = mutableListOf<Product>()
        val jsonProducts = JSONObject(json).getJSONObject("data").getJSONArray("products")

        for (i in 0 until jsonProducts.length()) {
            val jsonProduct = jsonProducts.getJSONObject(i)

            val id = jsonProduct.getInt("id")
            val name = jsonProduct.getString("name")
            val imageUrl = jsonProduct.getString("imageUrl")
            val price = Price(jsonProduct.getInt("price"))

            val product = Product(id.toLong(), name, imageUrl, price)
            products.add(product)
        }

        return products
    }
}
