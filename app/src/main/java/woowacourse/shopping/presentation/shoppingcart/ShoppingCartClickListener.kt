package woowacourse.shopping.presentation.shoppingcart

import woowacourse.shopping.domain.model.Goods

fun interface ShoppingCartClickListener {
    fun onDeleteGoods(goods: Goods)
}
