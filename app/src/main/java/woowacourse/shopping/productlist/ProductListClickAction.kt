package woowacourse.shopping.productlist

import woowacourse.shopping.util.CartItemCountClickAction

interface ProductListClickAction : CartItemCountClickAction {
    fun onProductClicked(id: Long)

    fun onIntoCartClicked(id: Long)
}
