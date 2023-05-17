package woowacourse.shopping.util

import java.text.DecimalFormat

private val DECIMAL_FORMATTER = DecimalFormat("#,###")

fun convertToMoneyFormat(money: Int): String = DECIMAL_FORMATTER.format(money)
