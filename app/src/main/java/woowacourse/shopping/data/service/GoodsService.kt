package woowacourse.shopping.data.service

import woowacourse.shopping.data.entity.GoodsEntity

interface GoodsService {
    fun getPagedGoods(
        page: Int,
        count: Int,
    ): Result<List<GoodsEntity>>

    fun getGoodsById(id: Int): Result<GoodsEntity?>

    fun getGoodsListByIds(id: List<Int>): Result<List<GoodsEntity>>
}
