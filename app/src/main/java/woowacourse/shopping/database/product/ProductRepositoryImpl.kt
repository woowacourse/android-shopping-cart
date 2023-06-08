package woowacourse.shopping.database.product

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.domain.Product
import woowacourse.shopping.mock.ProductMockWebServer
import woowacourse.shopping.repository.ProductRepository

object ProductRepositoryImpl : ProductRepository {

    private val productWebServer = ProductMockWebServer()
    private val client = OkHttpClient()

    override fun findAll(): List<Product> {
        val request = Request.Builder().url("${productWebServer.url}/products").build()
        val response = execute(request) ?: return emptyList()

        return parseProductsResponse(JSONArray(response))
    }

    override fun findAll(limit: Int, offset: Int): List<Product> {
        val request =
            Request.Builder().url("${productWebServer.url}/products?offset=$offset&count=$limit").build()
        val response = execute(request) ?: return emptyList()

        return parseProductsResponse(JSONArray(response))
    }

    override fun findById(id: Long): Product? {
        val request = Request.Builder().url("${productWebServer.url}/products/$id").build()
        val response = execute(request) ?: return null

        return parseProductResponse(JSONObject(response))
    }

    private fun execute(request: Request): String? {
        var result: String? = null

        val thread = Thread {
            val response = client.newCall(request).execute()
            result = response.body?.string()
        }
        thread.start()
        thread.join()

        return result
    }

    private fun parseProductsResponse(jsonArray: JSONArray): List<Product> {
        val products = mutableListOf<Product>()

        for (index in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(index)
            products.add(parseProductResponse(jsonObject))
        }

        return products
    }

    private fun parseProductResponse(jsonObject: JSONObject): Product {
        val id = jsonObject.getLong("id")
        val name = jsonObject.getString("name")
        val price = jsonObject.getInt("price")
        val imageUrl = jsonObject.getString("imageUrl")

        return Product(id, imageUrl, name, price)
    }
}
