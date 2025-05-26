package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.common.QuantityChangeListener

interface CartClickListener : QuantityChangeListener {
    fun onClickRemoveButton(cartId: Long)
}
