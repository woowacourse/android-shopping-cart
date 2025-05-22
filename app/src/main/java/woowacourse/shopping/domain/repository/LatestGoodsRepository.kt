package woowacourse.shopping.domain.repository

interface LatestGoodsRepository {
    fun insertLatestGoods(goodsId: Int)
}
