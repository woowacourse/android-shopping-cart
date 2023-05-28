package woowacourse.shopping.data.util

import org.json.JSONObject
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

fun String.convertJsonToProducts(): List<Product> {
    val products = mutableListOf<Product>()
    val jsonProducts = JSONObject(this)
        .getJSONObject("data")
        .getJSONArray("products")

    for (i in 0 until jsonProducts.length()) {
        val jsonProduct = jsonProducts.getJSONObject(i)

        val id = jsonProduct.getInt("id")
        val name = jsonProduct.getString("name")
        val imageUrl = jsonProduct.getString("imageUrl")
        val price = Price(jsonProduct.getInt("price"))

        val product = Product(id, name, imageUrl, price)
        products.add(product)
    }

    return products
}
