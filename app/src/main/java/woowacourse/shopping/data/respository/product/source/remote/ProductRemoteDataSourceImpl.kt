package woowacourse.shopping.data.respository.product.source.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.data.respository.product.ProductWebServer.PORT
import woowacourse.shopping.data.respository.product.ProductWebServer.startServer

class ProductRemoteDataSourceImpl : ProductRemoteDataSource {
    init {
        startServer()
    }

    override fun requestDatas(startPosition: Int): List<ProductEntity> {
        var newProducts: List<ProductEntity> = emptyList()

        val thread = Thread {
            val client = OkHttpClient()
            val host = "http://localhost:$PORT"
            val path = "/shopping/products?$startPosition"
            val request = Request.Builder().url(host + path).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@Thread
            newProducts = parseProductList(body)
        }
        thread.start()
        thread.join()

        return newProducts
    }

    override fun requestData(productId: Long): ProductEntity {
        var newProducts: ProductEntity = ProductEntity.errorData

        val thread = Thread {
            val client = OkHttpClient()
            val host = "http://localhost:$PORT"
            val path = "/shopping/products/$productId"
            val request = Request.Builder().url(host + path).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@Thread
            newProducts = parseProduct(JSONObject(body))
        }
        thread.start()
        thread.join()

        return newProducts
    }

    private fun parseProductList(response: String): List<ProductEntity> {
        val products = mutableListOf<ProductEntity>()
        val jsonArray = JSONArray(response)

        for (index in 0 until jsonArray.length()) {
            val json = jsonArray.getJSONObject(index) ?: continue
            products.add(parseProduct(json))
        }

        return products
    }

    private fun parseProduct(json: JSONObject): ProductEntity {
        val id = json.getLong("id")
        val name = json.getString("name")
        val price = json.getInt("price")
        val image = json.getString("imageUrl")

        return ProductEntity(id, name, price, image)
    }
}
