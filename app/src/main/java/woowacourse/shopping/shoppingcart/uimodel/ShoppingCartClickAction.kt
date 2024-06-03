package woowacourse.shopping.shoppingcart.uimodel

import woowacourse.shopping.util.CartItemCountClickAction

interface ShoppingCartClickAction : CartItemCountClickAction {
    fun onItemRemoveBtnClicked(id: Long)
}
