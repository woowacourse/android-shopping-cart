package woowacourse.shopping.presentation.util

interface QuantitySelectorListener {
    fun onIncreaseQuantity(position: Int)

    fun onDecreaseQuantity(position: Int)
}
