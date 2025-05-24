package woowacourse.shopping.presentation.goods.list

import woowacourse.shopping.presentation.util.QuantitySelectorListener

interface GoodsClickListener : QuantitySelectorListener {
    fun onGoodsClick(selectedGoodsId: Int)

    fun onPlusClick(position: Int)
}
