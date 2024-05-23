package woowacourse.shopping.productdetail

import woowacourse.shopping.util.CartItemCountClickAction

interface ProductDetailClickAction : CartItemCountClickAction {
    fun onAddCartClickAction()
}
