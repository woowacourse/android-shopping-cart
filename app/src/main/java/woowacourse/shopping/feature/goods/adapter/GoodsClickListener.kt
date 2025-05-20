package woowacourse.shopping.feature.goods.adapter

import woowacourse.shopping.domain.model.Goods

interface GoodsClickListener {
    fun onClickGoods(goods: Goods)
}
