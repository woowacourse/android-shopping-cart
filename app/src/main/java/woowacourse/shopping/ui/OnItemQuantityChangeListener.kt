package woowacourse.shopping.ui

interface OnItemQuantityChangeListener {
    fun onAdd(productId: Long)

    fun onIncrease(productId: Long)

    fun onDecrease(productId: Long)
}
