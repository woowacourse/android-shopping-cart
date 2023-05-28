package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel

interface CartProductListener {
    fun onCartItemRemoveButtonClick(cartProductModel: CartProductModel)

    fun onCheckBoxClick(cartProductModel: CartProductModel)

    fun onMinusAmountButtonClick(cartProductModel: CartProductModel)

    fun onPlusAmountButtonClick(cartProductModel: CartProductModel)
}
