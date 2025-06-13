package woowacourse.shopping.view.product

interface OnQuantityControlListener {
    fun onQuantityControlPlusClick(productId: Long)

    fun onQuantityControlMinusClick(productId: Long)
}
