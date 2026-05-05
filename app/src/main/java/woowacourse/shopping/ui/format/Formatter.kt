package woowacourse.shopping.ui.format

import java.text.DecimalFormat

object Formatter {
    private val format = DecimalFormat("#,###")
    fun priceFormat(price: Int): String = "${format.format(price)}원"
}
