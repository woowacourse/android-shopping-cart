package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.goods.Goods

interface RecentGoodsRepository {
    fun addRecentGoods(
        time: Long,
        goods: Goods,
        onResult: (Result<Unit>) -> Unit,
    )

    fun getLatestRecentGoodsId(onResult: (Result<Long?>) -> Unit)

    fun getRecentGoodsIds(onResult: (Result<List<Long>?>) -> Unit)
}
