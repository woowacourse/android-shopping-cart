package woowacourse.shopping.features.constant

object Format {
    fun formatPrice(price: Int): String = String.format("%,d원", price)
}
