package woowacourse.shopping.presentation.goods.list

import woowacourse.shopping.domain.model.goods.Goods

fun interface GoodsClickListener {
    fun onGoodsClick(goods: Goods)
}
