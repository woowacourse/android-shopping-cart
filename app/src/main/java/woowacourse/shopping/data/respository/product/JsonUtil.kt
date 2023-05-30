package woowacourse.shopping.data.respository.product

import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.data.model.ProductEntity

fun ProductEntity.toJson(): String {
    val jsonArray = JSONArray()
    jsonArray.put(
        JSONObject()
            .put("id", id)
            .put("title", title)
            .put("price", price)
            .put("imageUrl", imageUrl),
    )
    return jsonArray.toString()
}

fun JSONObject.toProduct(): ProductEntity {
    return ProductEntity(
        id = getLong("id"),
        title = getString("title"),
        imageUrl = getString("imageUrl"),
        price = getInt("price")
    )
}
