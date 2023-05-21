package woowacourse.shopping.data.datasource.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.mock.ProductMock
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

class ProductRemoteDao : ProductDataSource {
    private lateinit var mockWebServer: MockWebServer
    private val products: List<Product> = (0 until 100).map {
        ProductMock.make(it)
    }

    override fun selectByRange(start: Int, range: Int): List<Product> {
        var products: List<Product> = emptyList()
        val thread = Thread {
            val client = OkHttpClient()
            val host = "http://localhost:$PORT/"
            val path = "products?start=$start&range=$range"
            val request = Request.Builder().url(host + path).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@Thread
            val json = JSONArray(body)
            products = (0 until json.length()).map {
                makeProduct(json.getJSONObject(it))
            }
        }
        thread.start()
        thread.join()
        return products
    }

    override fun initMockData() {
        Thread {
            if (::mockWebServer.isInitialized) return@Thread
            mockWebServer = MockWebServer()
            mockWebServer.start(PORT)
            mockWebServer.url("/")
            mockWebServer.dispatcher = makeProductMockDispatcher()
        }.start()
    }

    private fun makeProduct(jsonObject: JSONObject): Product {
        return Product(
            jsonObject.getInt("id"),
            URL(jsonObject.getString("picture")),
            jsonObject.getString("title"),
            jsonObject.getInt("price")
        )
    }

    private fun makeProductMockDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: return MockResponse().setResponseCode(404)
            return when {
                path.contains("/products") -> {
                    val start = findParameterValue(path, "start").toInt()
                    val range = findParameterValue(path, "range").toInt()
                    MockResponse().setHeader("Content-Type", "application/json")
                        .setResponseCode(200).setBody(makeRangedProductsBody(start, range))
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    private fun findParameterValue(path: String, name: String): String =
        path.substringAfter("$name=").substringBefore("&")

    private fun makeRangedProductsBody(start: Int, range: Int): String {
        return products.subList(start, range).joinToString(", ") {
            makeProductJson(it)
        }.let {
            "[$it]"
        }
    }

    private fun makeProductJson(product: Product): String {
        return """{
            "id": ${product.id},
            "picture":"${product.picture.value}",
            "title": "${product.title}",
            "price": ${product.price}
        }
        """.trimIndent()
    }

    companion object {
        private const val PORT = 3365
    }
}
