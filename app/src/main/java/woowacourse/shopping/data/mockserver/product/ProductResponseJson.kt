package woowacourse.shopping.data.mockserver.product

import com.google.gson.Gson
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct

class ProductResponseJson {
    val productResponseTable = mutableMapOf<String, String>()

    private val gson = Gson()

    val totalProducts = List(66) {
        DataProduct(
            it,
            "$it",
            DataPrice(1000),
            "https://pbs.twimg.com/media/FpFzjV-aAAAIE-v?format=jpg&name=large"
        )
    }

    init {
        productResponseTable["/product?lastId=-1size=21"] =
            gson.toJson(totalProducts.subList(0, 21))
        productResponseTable["/product?lastId=19size=21"] =
            gson.toJson(totalProducts.subList(20, 41))
        productResponseTable["/product?lastId=39size=21"] =
            gson.toJson(totalProducts.subList(40, 49))
    }
}
