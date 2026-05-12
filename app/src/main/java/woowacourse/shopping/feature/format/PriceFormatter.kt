package woowacourse.shopping.feature.format

fun interface PriceFormatter {
    fun format(price: Int): String
}
