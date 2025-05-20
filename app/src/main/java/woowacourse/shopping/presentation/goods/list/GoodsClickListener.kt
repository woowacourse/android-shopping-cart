package woowacourse.shopping.presentation.goods.list

import woowacourse.shopping.domain.model.Goods

fun interface GoodsClickListener {
    fun onGoodsClick(goods: Goods)
}
