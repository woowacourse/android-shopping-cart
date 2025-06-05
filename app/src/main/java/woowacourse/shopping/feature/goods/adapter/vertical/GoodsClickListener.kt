package woowacourse.shopping.feature.goods.adapter.vertical

import woowacourse.shopping.domain.model.Goods

fun interface GoodsClickListener {
    fun onClickGoods(goods: Goods)
}
