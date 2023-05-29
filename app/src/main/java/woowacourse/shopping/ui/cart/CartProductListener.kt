package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.model.CartProductModel

interface CartProductListener {
    fun onCartItemRemoveButtonClick(cartProductModel: CartProductModel)

    fun onCheckBoxClick(cartProductModel: CartProductModel)

    fun onMinusAmountButtonClick(cartProductModel: CartProductModel)

    fun onPlusAmountButtonClick(cartProductModel: CartProductModel)
}
