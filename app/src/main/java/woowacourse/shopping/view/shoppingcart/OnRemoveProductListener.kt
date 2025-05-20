package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.ShoppingProduct

fun interface OnRemoveProductListener {
    fun onClickCancel(shoppingProduct: ShoppingProduct)
}
