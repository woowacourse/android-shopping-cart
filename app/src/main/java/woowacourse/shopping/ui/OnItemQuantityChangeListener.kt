package woowacourse.shopping.ui

interface OnItemQuantityChangeListener {
    fun onIncrease(productId: Long)

    fun onDecrease(productId: Long)
}
