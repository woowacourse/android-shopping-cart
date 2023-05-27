package woowacourse.shopping.data.product

import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product

fun Product.toJson(): String {
    val jsonArray = JSONArray()
    jsonArray.put(
        JSONObject()
            .put("id", id)
            .put("name", name)
            .put("price", price)
            .put("imageUrl", imageUrl),
    )
    return jsonArray.toString()
}

fun JSONObject.toProduct(): Product {
    return Product(
        id = getLong("id"),
        name = getString("name"),
        imageUrl = getString("imageUrl"),
        price = Price(getInt("price")),
    )
}
