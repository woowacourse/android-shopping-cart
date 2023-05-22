package woowacourse.shopping.data.mapper

import org.json.JSONObject
import woowacourse.shopping.Price
import woowacourse.shopping.Product

fun JSONObject.toProduct(): Product {
    return Product(
        id = getInt("id"),
        imageUrl = getString("imageUrl"),
        name = getString("name"),
        price = Price(getInt("price")),
    )
}
