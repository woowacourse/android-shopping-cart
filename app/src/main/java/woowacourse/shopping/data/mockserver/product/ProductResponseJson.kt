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
        productResponseTable["product/-1"] = gson.toJson(totalProducts.subList(0, 21))
        productResponseTable["product/20"] = gson.toJson(totalProducts.subList(20, 41))
        productResponseTable["product/40"] = gson.toJson(totalProducts.subList(40, 49))
    }
}
