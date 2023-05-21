package woowacourse.shopping.util

import java.text.DecimalFormat

fun Int.toMoneyFormat(): String {
    return DecimalFormat("#,###").format(this)
}
