package woowacourse.shopping.view.cart

interface QuantityClickListener {
    fun onQuantityPlusButtonClick(productId: Long)

    fun onQuqntityMinusButtonClick(productId: Long)
}
