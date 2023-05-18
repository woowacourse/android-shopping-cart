package woowacourse.shopping.model

import org.json.JSONArray
import org.json.JSONObject

data class Product(val id: Int, val name: String, val price: Int, val imageUrl: String) {
    fun toJson(): String {
        val jsonArray = JSONArray()
        jsonArray.put(
            JSONObject()
                .put("id", id)
                .put("name", name)
                .put("price", price)
                .put("imageUrl", imageUrl)
        )
        return jsonArray.toString()
    }

    companion object {
        fun fromJson(jSONObject: JSONObject): Product {
            return Product(
                jSONObject.getInt("id"),
                jSONObject.getString("name"),
                jSONObject.getInt("price"),
                jSONObject.getString("imageUrl")
            )
        }
    }
}
