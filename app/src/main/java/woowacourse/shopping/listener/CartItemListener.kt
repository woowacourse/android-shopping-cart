package woowacourse.shopping.listener

import woowacourse.shopping.ui.cart.uistate.CartUIState

interface CartItemListener : CountListener {
    override fun onPlusCountButtonClick(productId: Long, oldCount: Int)
    override fun onMinusCountButtonClick(productId: Long, oldCount: Int)
    fun onCloseButtonClick(productId: Long)
    fun onCheckboxClick(isChecked: Boolean, item: CartUIState)
}
