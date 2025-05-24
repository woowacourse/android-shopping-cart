package woowacourse.shopping.presentation.shoppingcart

import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.util.QuantitySelectorListener

interface ShoppingCartClickListener : QuantitySelectorListener {
    fun onDeleteGoods(goods: GoodsUiModel)
}
