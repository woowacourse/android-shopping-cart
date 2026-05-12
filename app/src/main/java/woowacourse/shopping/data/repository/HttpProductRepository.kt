package woowacourse.shopping.data.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName
import java.io.IOException

class HttpProductRepository(
    private val baseUrl: String,
    private val client: OkHttpClient,
) : ProductRepository {
    override suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product> {
        val request =
            Request
                .Builder()
                .url("${baseUrl}products")
                .build()

        val responseBody =
            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("상품 목록 조회에 실패했습니다. code=${response.code}")
                    }
                    response.body.string()
                }
            }

        val jsonArray = JSONArray(responseBody)

        return (0 until jsonArray.length())
            .map { index -> jsonArray.getJSONObject(index).toProduct() }
            .drop(offset)
            .take(limit)
            .toImmutableList()
    }

    override suspend fun getProductById(id: String): Product {
        val request =
            Request
                .Builder()
                .url("${baseUrl}products/$id")
                .build()

        val responseBody =
            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("상품 조회에 실패했습니다. code=${response.code}")
                    }
                    response.body.string()
                }
            }

        return JSONObject(responseBody).toProduct()
    }

    private fun JSONObject.toProduct(): Product =
        Product(
            id = getLong("id").toString(),
            name = ProductName(getString("name")),
            price = Money(getInt("price")),
            imageUrl = getString("imageUrl"),
        )
}
