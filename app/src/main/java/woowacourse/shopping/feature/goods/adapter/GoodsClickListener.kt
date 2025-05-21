package woowacourse.shopping.feature.goods.adapter

import woowacourse.shopping.domain.model.Goods

fun interface GoodsClickListener {
    fun onClickGoods(goods: Goods)
}
