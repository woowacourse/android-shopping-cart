package woowacourse.shopping.presentation.cart

interface CartCounterClickListener {
    fun onClickMinus(id: Long)

    fun onClickPlus(id: Long)
}
