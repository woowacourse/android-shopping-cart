package woowacourse.shopping.feature.format

import java.text.DecimalFormat

class DecimalPriceFormatter(
    private val suffix: String = "원",
) : PriceFormatter {
    private val format = DecimalFormat("#,###")
    override fun format(price: Int) = "${format.format(price)}$suffix"
}

// class DecimalPriceFormatter(val format: Format, val suffix: String) : Formatter(format) {
//    override fun apply(price: Int): String {
//        return "${format.format(price)}$suffix"
//    }
// }
