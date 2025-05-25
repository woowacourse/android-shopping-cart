package woowacourse.shopping.presentation.util

interface QuantitySelectorListener {
    fun onIncreaseQuantity(goodsId: Int)

    fun onDecreaseQuantity(goodsId: Int)
}
