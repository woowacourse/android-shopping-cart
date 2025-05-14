package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.Product

fun interface OnRemoveProductListener {
    fun onClickCancel(product: Product)
}
