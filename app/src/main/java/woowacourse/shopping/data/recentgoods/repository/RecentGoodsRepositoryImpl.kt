package woowacourse.shopping.data.recentgoods.repository

import woowacourse.shopping.data.recentgoods.database.RecentGoodsDao
import woowacourse.shopping.data.recentgoods.mapper.toEntity
import woowacourse.shopping.data.util.doAsyncCatching
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.RecentGoodsRepository

class RecentGoodsRepositoryImpl(
    private val dao: RecentGoodsDao
) : RecentGoodsRepository {
    override fun addRecentGoods(
        goods: Goods,
        onResult: (Result<Unit>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                dao.insertGoods(goods.toEntity())
                dao.trimGoods(MAX_RECENT_GOODS)
            },
            onResult = onResult
        )
    }

    override fun getLatestRecentGoods(onResult: (Result<Long?>) -> Unit) {
        doAsyncCatching(
            block = {
                dao.getLatestRecentGoods()?.id
            },
            onResult = onResult
        )
    }

    override fun getRecentGoods(onResult: (Result<List<Long>?>) -> Unit) {
        doAsyncCatching(
            block = {
                dao.getRecentGoods(MAX_RECENT_GOODS)?.map { it.id }
            },
            onResult = onResult
        )
    }

    companion object {
        private const val MAX_RECENT_GOODS = 10
    }
}