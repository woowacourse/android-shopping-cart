package woowacourse.shopping.data.service

import com.example.domain.datasource.firstJsonProducts
import com.example.domain.datasource.secondJsonProducts
import com.example.domain.datasource.thirdJsonProducts
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
import org.json.JSONArray
import java.io.IOException

class MockProductRemoteService {
    private var _mockWebServer: MockWebServer? = null
    private val mockWebServer: MockWebServer
        get() = _mockWebServer!!

    private val dispatcher: Dispatcher

    private lateinit var baseUrl: String

    init {
        // 디스패쳐 모드로 응답을 결정
        dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/products?lastProductId=0" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(SUCCESS_RESPONSE_CODE)
                            .setBody(firstJsonProducts)
                    }
                    "/products?lastProductId=20" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(SUCCESS_RESPONSE_CODE)
                            .setBody(secondJsonProducts)
                    }
                    "/products?lastProductId=40" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(SUCCESS_RESPONSE_CODE)
                            .setBody(thirdJsonProducts)
                    }
                    else -> {
                        MockResponse().setResponseCode(ERROR_RESPONSE_CODE)
                    }
                }
            }
        }
    }

    fun request(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        synchronized(this) {
            if (_mockWebServer == null) {
                val thread = Thread {
                    _mockWebServer = MockWebServer()
                    _mockWebServer?.url("/")
                    _mockWebServer?.dispatcher = dispatcher
                    baseUrl = String.format("http://localhost:%s", mockWebServer.port)
                }
                thread.start()
                thread.join()
            }
        }

        val url = "$baseUrl/products?lastProductId=$lastProductId"
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code >= ERROR_RESPONSE_CODE) return onFailure()
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
            val product = Product(id.toLong(), name, imageUrl, price)
            productList.add(product)
        }

        return productList
    }

    companion object {
        private const val SUCCESS_RESPONSE_CODE = 200
        private const val ERROR_RESPONSE_CODE = 404
    }
}
