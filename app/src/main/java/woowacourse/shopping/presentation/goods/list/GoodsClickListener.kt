package woowacourse.shopping.presentation.goods.list

import woowacourse.shopping.presentation.model.GoodsUiModel

fun interface GoodsClickListener {
    fun onGoodsClick(goods: GoodsUiModel)
}
