package woowacourse.shopping.database.product

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.ProductRepository
import java.lang.IllegalStateException

object ServerProductRepository : ProductRepository {

    private val mockServer = MockServer()

    private val baseUrl by lazy { "http://localhost:${mockServer.port}" }

    init {
        mockServer.run()
    }

    override fun findAll(limit: Int, offset: Int): List<Product> {
        var products: List<Product> = emptyList()
        val thread = Thread {
            val client = OkHttpClient()
            val path = "/products?limit=$limit&offset=$offset"
            val request = Request.Builder().url(baseUrl + path).build()
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
            val path = "/products/count"
            val request = Request.Builder().url(baseUrl + path).build()
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
            val path = "/products/$id"
            val request = Request.Builder().url(baseUrl + path).build()
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
}