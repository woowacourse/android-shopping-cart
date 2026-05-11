package woowacourse.shopping.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle

class ProductRemoteDataSource(
    private val baseUrl: String,
) {
    private val client: OkHttpClient = OkHttpClient()

    suspend fun getTotalSize(): Int {
        val responseBody = get("/product/size") ?: return 0
        return JSONObject(responseBody).getInt("size")
    }

    suspend fun getProduct(productId: String): Product? {
        val responseBody = get("/product/$productId") ?: return null
        return JSONObject(responseBody).toProduct()
    }

    suspend fun getProducts(
        offset: Int,
        size: Int,
    ): List<Product> {
        val responseBody = get("/products?offset=$offset&size=$size") ?: return emptyList()
        val jsonArray = JSONArray(responseBody)

        return (0 until jsonArray.length()).map { index ->
            jsonArray.getJSONObject(index).toProduct()
        }
    }

    private suspend fun get(path: String): String? =
        withContext(Dispatchers.IO) {
            val request =
                Request
                    .Builder()
                    .url("$baseUrl$path")
                    .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                response.body.string()
            }
        }

    private fun JSONObject.toProduct(): Product =
        Product(
            id = getInt("id").toString(),
            title = ProductTitle(getString("name")),
            price = Price(getInt("price")),
            imageUrl = getString("imageUrl"),
        )
}
