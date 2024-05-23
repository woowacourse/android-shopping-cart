package woowacourse.shopping.presentation.home

interface QuantityListener {
    fun onQuantityChange(
        productId: Long,
        quantity: Int,
    )
}
