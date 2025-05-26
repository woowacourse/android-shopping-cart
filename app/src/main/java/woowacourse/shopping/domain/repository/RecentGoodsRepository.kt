package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Goods

interface RecentGoodsRepository {
    fun addRecentGoods(
        goods: Goods,
        onResult: (Result<Unit>) -> Unit,
    )

    fun getLatestRecentGoods(onResult: (Result<Long?>) -> Unit)

    fun getRecentGoods(onResult: (Result<List<Long>?>) -> Unit)
}
