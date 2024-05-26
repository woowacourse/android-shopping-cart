package woowacourse.shopping.ui

interface CountButtonClickListener {
    fun plusCount(productId: Long)

    fun minusCount(productId: Long)
}
