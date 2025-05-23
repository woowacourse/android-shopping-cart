package woowacourse.shopping.ui.common

interface QuantityChangeListener {
    fun increase(cartId: Long)
    fun decrease(cartId: Long)
}