package woowacourse.shopping.database.product

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.ProductRepository
import java.lang.IllegalStateException

object ServerProductRepository : ProductRepository {

    private val REGEX_REQUEST_PRODUCT = Regex("^/products/\\d+$")
    private val REGEX_REQUEST_PRODUCTS = Regex("^/products\\?limit=\\d+&offset=\\d+$")
    private val REGEX_REQUEST_PRODUCTS_COUNT = Regex("^/products/count")

    lateinit var mockWebServer: MockWebServer

    private val products: List<String> = (1..102).map { index ->
        """
            {  
                "id": $index,
                "imageUrl": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvcCPDO5TRkS6bHmemx0262nWeXizH3fD8fJPsLHc2GxDqKCqMWeOYFK3HOJu5VKpaAH0&usqp=CAU",
                "name": "Product $index",
                "price": 10000
            }
        """.trimIndent()
    }

    init {
        val thread = Thread {
            if (::mockWebServer.isInitialized) return@Thread
            mockWebServer = MockWebServer()
            mockWebServer.url("/")
            mockWebServer.dispatcher = makeProductMockDispatcher()
        }
        thread.start()
    }

    override fun findAll(limit: Int, offset: Int): List<Product> {
        var products: List<Product> = emptyList()
        val thread = Thread {
            val client = OkHttpClient()
            val host = "http://localhost:${mockWebServer.port}"
            val path = "/products?limit=$limit&offset=$offset"
            val request = Request.Builder().url(host + path).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@Thread
            val json = JSONArray(body)
            products = (0 until json.length()).map {
                val jsonObject = json.getJSONObject(it)
                parseToProduct(jsonObject)
            }
        }
        thread.start()
        thread.join()
        return products
    }

    override fun countAll(): Int {
        var count: Int? = null
        val thread = Thread {
            val client = OkHttpClient()
            val host = "http://localhost:${mockWebServer.port}"
            val path = "/products/count"
            val request = Request.Builder().url(host + path).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@Thread
            count = JSONObject(body).getInt("count")

        }
        thread.start()
        thread.join()
        return count ?: throw IllegalStateException("json의 형식이 맞지 않은듯함")
    }

    override fun findById(id: Long): Product? {
        var product: Product? = null
        val thread = Thread {
            val client = OkHttpClient()
            val host = "http://localhost:${mockWebServer.port}"
            val path = "/products/$id"
            val request = Request.Builder().url(host + path).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@Thread
            product = parseToProduct(JSONObject(body))
        }
        thread.start()
        thread.join()
        return product
    }

    private fun parseToProduct(jsonObject: JSONObject): Product {
        val id = jsonObject.getLong("id")
        val name = jsonObject.getString("name")
        val price = jsonObject.getInt("price")
        val imageUrl = jsonObject.getString("imageUrl")
        return Product(id, imageUrl, name, price)
    }

    private fun makeProductMockDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path
            if (path.isNullOrBlank()) {
                return MockResponse().setResponseCode(400)
            }

            return when {
                REGEX_REQUEST_PRODUCT.matches(path) -> {
                    val productId = path.removePrefix("/products/").toInt() - 1
                    MockResponse().setResponseCode(200)
                        .setBody(products[productId])
                }

                REGEX_REQUEST_PRODUCTS.matches(path) -> {
                    val limit = findParameterValue(path, "limit").toInt()
                    val offset = findParameterValue(path, "offset").toInt()
                    val products = products.slice(offset until products.size)
                        .take(limit)
                    val body = products.joinToString(
                        prefix = "[",
                        postfix = "]",
                        separator = ",\n"
                    ) { it }
                    MockResponse().setResponseCode(200).setBody(body)
                }

                REGEX_REQUEST_PRODUCTS_COUNT.matches(path) -> {
                    MockResponse().setResponseCode(200)
                        .setBody("{ \"count\": ${products.size} }")
                }

                else -> {
                    MockResponse().setResponseCode(400)
                }
            }
        }
    }

    private fun findParameterValue(path: String, name: String): String =
        path.substringAfter("$name=").substringBefore("&")
}