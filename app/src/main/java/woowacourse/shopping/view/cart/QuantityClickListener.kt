package woowacourse.shopping.view.cart

interface QuantityClickListener {
    fun onPlusButtonClick(productId: Long)

    fun onMinusButtonClick(productId: Long)
}
