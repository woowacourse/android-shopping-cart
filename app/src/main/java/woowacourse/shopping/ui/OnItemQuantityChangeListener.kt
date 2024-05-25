package woowacourse.shopping.ui

interface OnItemQuantityChangeListener {
    fun onIncrease(productId: Int)

    fun onDecrease(productId: Int)
}
