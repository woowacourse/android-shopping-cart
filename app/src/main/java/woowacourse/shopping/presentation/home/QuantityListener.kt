package woowacourse.shopping.presentation.home

interface QuantityListener {
    fun onQuantityChange(
        productId: Long,
        cartItemId: Long?,
        quantity: Int,
    )
}
