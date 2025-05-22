package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.LatestGoods

interface LatestGoodsRepository {
    fun insertLatestGoods(goodsId: Int)

    fun getAll(): List<LatestGoods>
}
