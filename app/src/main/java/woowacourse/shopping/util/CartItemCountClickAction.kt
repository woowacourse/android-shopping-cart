package woowacourse.shopping.util

interface CartItemCountClickAction {
    fun onPlusCountClicked(id: Long)

    fun onMinusCountClicked(id: Long)
}
