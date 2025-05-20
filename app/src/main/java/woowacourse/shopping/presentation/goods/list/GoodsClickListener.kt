package woowacourse.shopping.presentation.goods.list

import woowacourse.shopping.presentation.model.GoodsUiModel

interface GoodsClickListener {
    fun onGoodsClick(goods: GoodsUiModel)

    fun onPlusClick(position: Int)
}
