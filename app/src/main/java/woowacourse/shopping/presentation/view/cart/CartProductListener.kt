package woowacourse.shopping.presentation.view.cart

interface CartProductListener {
    fun onCheckChanged(cartId: Long, checked: Boolean)
    fun onCountClick(cartId: Long, count: Int)
    fun onDeleteClick(cartId: Long)
}
