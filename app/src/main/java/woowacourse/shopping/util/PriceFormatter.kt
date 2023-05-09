package woowacourse.shopping.util

import java.text.DecimalFormat

object PriceFormatter {
    fun format(price: Int): String {
        return DecimalFormat("#,###").format(price)
    }
}
