package woowacourse.shopping.core.util

import java.text.DecimalFormat

fun formattedPrice(value: Int): String {
    val decimal = DecimalFormat("#,###원")
    return decimal.format(value)
}
