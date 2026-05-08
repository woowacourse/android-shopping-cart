package woowacourse.shopping.ui.extension

import woowacourse.shopping.domain.Price

fun Price.toFormattedPrice(): String = "%,d원".format(value)
