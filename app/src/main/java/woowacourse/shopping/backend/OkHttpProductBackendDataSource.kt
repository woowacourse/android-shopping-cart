package woowacourse.shopping.backend

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.ShoppingItem
import java.io.IOException

class OkHttpProductBackendDataSource(
    private val client: OkHttpClient,
    private val baseUrl: HttpUrl,
) {
    suspend fun fetchProducts(): List<ShoppingItem> {
        val request =
            Request
                .Builder()
                .url(baseUrl.newBuilder().addPathSegment("products").build())
                .get()
                .build()

        client.newCall(request).execute().use { currentResponse ->
            if (!currentResponse.isSuccessful) {
                throw IOException("상품 조회 실패: HTTP ${currentResponse.code}")
            }
            val body = currentResponse.body.string()
            return parseProducts(body)
        }
    }

    private fun parseProducts(productsJson: String): List<ShoppingItem> {
        val productsArray = JSONArray(productsJson)
        return List(productsArray.length()) { index ->
            val productJson = productsArray.getJSONObject(index)
            val productTitle =
                productJson
                    .optString("name", productJson.optString("title"))
                    .trim()
            ShoppingItem(
                product =
                    Product(
                        id = productJson.getLong("id"),
                        title = ProductTitle(productTitle),
                        price = Price(productJson.getInt("price")),
                        imageUrl = productJson.getString("imageUrl"),
                    ),
                quantity = 0,
            )
        }
    }
}
