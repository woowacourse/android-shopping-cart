package woowacourse.shopping.presentation.shoppingcart

import woowacourse.shopping.domain.model.shoppingcart.ShoppingCartItem

fun interface ShoppingCartClickListener {
    fun onDeleteGoods(item: ShoppingCartItem)
}
