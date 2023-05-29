package woowacourse.shopping.datas

import com.shopping.domain.Price
import com.shopping.domain.Product
import com.shopping.domain.ProductRepository
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

class MockServerProductRepositoryImpl : ProductRepository {
    private var _mockWebServer: MockWebServer? = null
    private val mockWebServer: MockWebServer get() = _mockWebServer!!

    private var dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: MockResponse().setHeader("Content-Type", "application/json")
                .setResponseCode(404).toString()
            return when {
                path.startsWith("/products") -> {
                    val unitSize = path.substringAfter("unitsize=").substringBefore("&").toInt()
                    val pageNumber = path.substringAfter("pagenumber=").toInt()
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(getNext(unitSize, pageNumber))
                }

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private fun getNext(unitSize: Int, pageNumber: Int): String {
        return when (pageNumber) {
            1 -> productsPage1
            2 -> productsPage2
            else -> throw IllegalArgumentException("잘못된 pageNumber 입니다.")
        }
    }

    override fun getUnitData(
        unitSize: Int,
        pageNumber: Int,
        onFailure: () -> Unit,
        onSuccess: (products: List<Product>) -> Unit
    ) {
        synchronized(this) { // 동기화 (여러 응답 스레드 순차적 실행)
            if (_mockWebServer == null) {
                _mockWebServer = MockWebServer()
                _mockWebServer?.url("/")
                _mockWebServer?.dispatcher = dispatcher
            }

            val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
            val okHttpClient = OkHttpClient()
            val url = "$baseUrl/products?unitsize=$unitSize&pagenumber=$pageNumber"
            val request = Request.Builder().url(url).build()
            okHttpClient.newCall(request).enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (400 <= response.code) return
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

            val product = Product(id = id, name = name, imageUrl = imageUrl, price = Price(price))
            products.add(product)
        }

        return products
    }
}
