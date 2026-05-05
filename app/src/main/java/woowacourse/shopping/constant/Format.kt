package woowacourse.shopping.constant

object Format {
    fun formatPrice(price: Int): String = String.format("%,d원", price)
}
