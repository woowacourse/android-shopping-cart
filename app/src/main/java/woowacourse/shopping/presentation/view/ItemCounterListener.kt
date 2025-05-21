package woowacourse.shopping.presentation.view

interface ItemCounterListener {
    fun increase(productId: Long)

    fun decrease(productId: Long)
}
