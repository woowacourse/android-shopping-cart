package woowacourse.shopping.data.dao

import woowacourse.shopping.data.entity.GoodsEntity

interface GoodsDao {
    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<GoodsEntity>

    fun getGoodsById(id: Int): GoodsEntity
}
