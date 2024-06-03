package woowacourse.shopping.productdetail.uimodel

import woowacourse.shopping.util.CartItemCountClickAction

interface ProductDetailClickAction : CartItemCountClickAction {
    fun onAddCartClicked()

    fun onLastProductClicked()
}
