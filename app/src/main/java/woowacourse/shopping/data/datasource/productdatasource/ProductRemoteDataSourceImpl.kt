package woowacourse.shopping.data.datasource.productdatasource

import com.example.domain.Product
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.data.mock.ProductMockWebServer.PORT
import woowacourse.shopping.data.mock.ProductMockWebServer.startMockWebServer

class ProductRemoteDataSourceImpl : ProductDataSource {

    init {
        startMockWebServer()
    }

    override fun requestAllData(): List<Product> {
        val client = OkHttpClient()
        val url = URL + PORT
        val path = PATH_GET
        val request = Request.Builder().url(url + path).build()

        var result: String? = null

        val thread = Thread {
            val response = client.newCall(request).execute()
            result = response.body?.string()
        }
        thread.start()
        thread.join()

        val jsonArray = JSONArray(result)
        return parseProductsFrom(jsonArray)
    }

    private fun parseProductsFrom(jsonArray: JSONArray): List<Product> {
        val products = mutableListOf<Product>()

        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index) ?: continue
            products.add(parseProductFrom(jsonObject))
        }

        return products
    }

    private fun parseProductFrom(jsonObject: JSONObject): Product {
        val id = jsonObject.getInt("id")
        val imageUrl = jsonObject.getString("imageUrl")
        val name = jsonObject.getString("name")
        val price = jsonObject.getInt("price")

        return Product(id, imageUrl, name, price)
    }

    companion object {
        private const val URL = "http://localhost:"
        private const val PATH_GET = "/products"
    }
}
