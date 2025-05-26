package woowacourse.shopping.data.service

import woowacourse.shopping.data.entity.GoodsEntity

interface GoodsService {
    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<GoodsEntity>

    fun getGoodsById(id: Int): GoodsEntity?

    fun getGoodsListByIds(id: List<Int>): List<GoodsEntity>
}
