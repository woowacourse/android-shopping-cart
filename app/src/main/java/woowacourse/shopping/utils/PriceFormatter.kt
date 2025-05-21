package woowacourse.shopping.utils

import android.icu.text.DecimalFormat

object PriceFormatter {
    private const val PRICE_PATTERN = "#,###"

    fun format(price: Int): String {
        return DecimalFormat(PRICE_PATTERN).format(price)
    }
}
