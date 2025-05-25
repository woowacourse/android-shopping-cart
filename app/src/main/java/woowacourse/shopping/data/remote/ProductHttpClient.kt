package woowacourse.shopping.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import woowacourse.shopping.domain.product.Money
import woowacourse.shopping.domain.product.Product

class ProductHttpClient(private val url: String = "https://shoppingcart.com") {
    private val client = OkHttpClient()

    fun fetchProductList(): List<Product> {
        val request = Request.Builder()
            .url("$url/products")
            .build()

        val response: Response = client.newCall(request).execute()

        if (!response.isSuccessful) throw Exception("요청 실패: ${response.code}")

        val body = response.body?.string() ?: throw Exception("응답 없음")

        val jsonArray = JSONArray(body)
        val products = mutableListOf<Product>()

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val id = item.getLong("id")
            val imageUrl = item.getString("imageUrl")
            val name = item.getString("name")
            val price = item.getInt("price")
            products.add(Product(id, imageUrl, name, Money(price)))
        }

        return products
    }
}
