package woowacourse.shopping.ui.extension

fun Int.toFormattedPrice(): String = "%,d원".format(this)
