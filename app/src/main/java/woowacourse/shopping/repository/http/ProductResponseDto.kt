package woowacourse.shopping.repository.http

import org.json.JSONObject
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId

data class ProductResponseDto(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    fun toDomain(): Product =
        Product(
            id = ProductId.fromRemoteId(id),
            name = name,
            price = Money(price),
            imageUrl = imageUrl,
        )

    companion object {
        fun fromJson(jsonObject: JSONObject): ProductResponseDto =
            ProductResponseDto(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                price = jsonObject.getInt("price"),
                imageUrl = jsonObject.getString("imageUrl"),
            )
    }
}
