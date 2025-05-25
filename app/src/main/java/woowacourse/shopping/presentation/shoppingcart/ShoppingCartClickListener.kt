package woowacourse.shopping.presentation.shoppingcart

import woowacourse.shopping.domain.model.ShoppingCartItem

fun interface ShoppingCartClickListener {
    fun onDeleteGoods(item: ShoppingCartItem)
}
