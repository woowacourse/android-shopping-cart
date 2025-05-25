package woowacourse.shopping.ui.common

interface QuantityChangeListener {
    fun increase(productId: Long)
    fun decrease(productId: Long)
}