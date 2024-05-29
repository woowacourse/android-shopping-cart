package woowacourse.shopping.presentation.action

interface CartItemCountHandler {
    fun onCartItemAdd(id: Long)

    fun onCartItemMinus(id: Long)
}
