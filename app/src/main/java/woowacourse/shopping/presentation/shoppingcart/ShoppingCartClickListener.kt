package woowacourse.shopping.presentation.shoppingcart

import woowacourse.shopping.domain.model.Goods

interface ShoppingCartClickListener {
    fun onDeleteGoods(goods: Goods)
}
