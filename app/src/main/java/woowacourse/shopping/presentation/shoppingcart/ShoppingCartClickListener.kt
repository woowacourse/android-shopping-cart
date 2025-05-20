package woowacourse.shopping.presentation.shoppingcart

import woowacourse.shopping.presentation.model.GoodsUiModel

fun interface ShoppingCartClickListener {
    fun onDeleteGoods(goodsUiModel: GoodsUiModel)
}
