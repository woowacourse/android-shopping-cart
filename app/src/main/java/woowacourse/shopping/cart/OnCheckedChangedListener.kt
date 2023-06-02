package woowacourse.shopping.cart

import woowacourse.shopping.uimodel.CartProductUIModel

interface OnCheckedChangedListener {
    fun onChangedIsPicked(cartProductUIModel: CartProductUIModel, isPicked: Boolean)
}
