package woowacourse.shopping.common

import java.text.DecimalFormat

object PriceFormatter {
    fun format(price: Int): String {
        return DecimalFormat("#,###").format(price)
    }
}
