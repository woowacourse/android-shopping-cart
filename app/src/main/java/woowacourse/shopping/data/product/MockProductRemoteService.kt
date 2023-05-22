package woowacourse.shopping.data.product

import com.example.domain.Product
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
import org.json.JSONObject
import java.io.IOException

class MockProductRemoteService {
    private var _mockWebServer: MockWebServer? = null
    private val mockWebServer: MockWebServer get() = _mockWebServer!!

    private var dispatcher: Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products?lastProductId=0" -> {
                    MockResponse()
                        .setHeader("Contet-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(firstJsonProducts)
                }
                "/products?lastProductId=20" -> {
                    MockResponse()
                        .setHeader("Contet-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(secondJsonProducts)
                }
                "/products?lastProductId=40" -> {
                    MockResponse()
                        .setHeader("Contet-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(thirdJsonProducts)
                }
                else -> {
                    if (request.path!!.startsWith("/products?id")) {
                        val parts = request.path!!.split("/")
                        val lastPart = parts.last()
                        val numberParts = lastPart.split("=")
                        val number = numberParts.last().toLong()

                        MockResponse()
                            .setHeader("Contet-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(jsonMap[number]!!)
                    } else {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }
    }

    fun requestProduct(
        productId: Long,
        onSuccess: (Product?) -> Unit,
        onFailure: () -> Unit
    ) {
        synchronized(this) { // 동기화 (여러 응답 스레드 순차적 실행)
            if (_mockWebServer == null) {
                _mockWebServer = MockWebServer()
                _mockWebServer?.url("/")
                _mockWebServer?.dispatcher = dispatcher
            }

            val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
            val okHttpClient = OkHttpClient()
            val url = "$baseUrl/products?id=$productId"
            val request = Request.Builder().url(url).build()

            okHttpClient.newCall(request).enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (400 <= response.code) return onFailure()
                        val responseBody = response.body?.string()
                        response.close()

                        val result = responseBody?.let {
                            parseJsonToProductList2(it)
                        }

                        onSuccess(result)
                    }
                }
            )
        }
    }

    fun requestProductsUnit(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        synchronized(this) { // 동기화 (여러 응답 스레드 순차적 실행)
            if (_mockWebServer == null) {
                _mockWebServer = MockWebServer()
                _mockWebServer?.url("/")
                _mockWebServer?.dispatcher = dispatcher
            }

            val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
            val okHttpClient = OkHttpClient()
            val url = "$baseUrl/products?lastProductId=$lastProductId"
            val request = Request.Builder().url(url).build()

            okHttpClient.newCall(request).enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (400 <= response.code) return onFailure()
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
        val products: MutableList<Product> = mutableListOf()

        val jsonArray = JSONArray(responseString)
        for (index in 0 until jsonArray.length()) {
            val jsonProduct = jsonArray.getJSONObject(index)
            val id = jsonProduct.getInt("id")
            val name = jsonProduct.getString("name")
            val imageUrl = jsonProduct.getString("imageUrl")
            val price = jsonProduct.getInt("price")

            val product = Product(id = id, name = name, imageUrl = imageUrl, price = price)
            products.add(product)
        }

        return products
    }

    private fun parseJsonToProductList2(responseString: String): Product {
        val jsonObject = JSONObject(responseString)
        val id = jsonObject.getInt("id")
        val name = jsonObject.getString("name")
        val imageUrl = jsonObject.getString("imageUrl")
        val price = jsonObject.getInt("price")

        val product = Product(id = id, name = name, imageUrl = imageUrl, price = price)

        return product
    }
}
