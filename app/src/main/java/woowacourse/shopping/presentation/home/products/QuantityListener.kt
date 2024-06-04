package woowacourse.shopping.presentation.home.products

interface QuantityListener {
    fun onQuantityChange(
        productId: Long,
        quantity: Int,
    )
}
