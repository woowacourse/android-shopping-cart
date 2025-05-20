package woowacourse.shopping.presentation.goods.list

import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.util.QuantitySelectorListener

interface GoodsClickListener : QuantitySelectorListener {
    fun onGoodsClick(goods: GoodsUiModel)

    fun onPlusClick(position: Int)
}
