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
        val client = OkHttpClient()
        val url = "http://localhost:$PORT"
        val path = "$PRODUCTS?$startPosition"
        val request = Request.Builder().url(url + path).build()
        val result = request(client, request) ?: return emptyList()

        return parseProductList(result)
    }

    override fun requestData(productId: Long): ProductEntity {
        val client = OkHttpClient()
        val url = "http://localhost:$PORT"
        val path = "$PRODUCTS/$productId"
        val request = Request.Builder().url(url + path).build()
        val result = request(client, request) ?: return ProductEntity.errorData

        return parseProduct(JSONObject(result))
    }

    private fun request(client: OkHttpClient, request: Request): String? {
        var result: String? = null

        val thread = Thread {
            val response = client.newCall(request).execute()
            result = response.body?.string()
        }
        thread.start()
        thread.join()

        return result
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

    companion object {
        private const val PRODUCTS = "/shopping/products"
    }
}
