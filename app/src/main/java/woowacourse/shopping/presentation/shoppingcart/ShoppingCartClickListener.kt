package woowacourse.shopping.presentation.shoppingcart

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.util.QuantitySelectorListener

interface ShoppingCartClickListener : QuantitySelectorListener {
    fun onDeleteGoods(goods: Goods)
}
