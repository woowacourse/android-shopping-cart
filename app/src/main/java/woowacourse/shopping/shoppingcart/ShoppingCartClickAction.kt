package woowacourse.shopping.shoppingcart

import woowacourse.shopping.util.CartItemCountClickAction

interface ShoppingCartClickAction : CartItemCountClickAction {
    fun onItemRemoveBtnClicked(id: Long)
}
