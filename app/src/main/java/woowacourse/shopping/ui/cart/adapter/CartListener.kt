package woowacourse.shopping.ui.cart.adapter

import woowacourse.shopping.ui.cart.uistate.CartUIState

interface CartListener {
    fun onCloseButtonClick(productId: Long)
    fun onPlusCountButtonClick(productId: Long, oldCount: Int)
    fun onMinusCountButtonClick(productId: Long, oldCount: Int)
    fun onCheckboxClick(isChecked: Boolean, item: CartUIState)
}
