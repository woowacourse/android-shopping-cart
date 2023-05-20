package woowacourse.shopping.model

import java.io.Serializable
import java.text.DecimalFormat

data class ProductUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    var count: Int = 0,
) : Serializable {

    fun toPriceFormat(): String {
        return DecimalFormat("#,###").format(price)
    }
}
