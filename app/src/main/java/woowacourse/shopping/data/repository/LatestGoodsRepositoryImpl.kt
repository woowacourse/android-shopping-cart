package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.LatestGoodsDao
import woowacourse.shopping.data.entity.LatestGoodsEntity
import woowacourse.shopping.data.entity.toLatestGoods
import woowacourse.shopping.domain.model.LatestGoods
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import kotlin.concurrent.thread

class LatestGoodsRepositoryImpl(
    private val latestGoodsDao: LatestGoodsDao,
) : LatestGoodsRepository {
    override fun insertLatestGoods(goodsId: Int) {
        thread {
            latestGoodsDao.insert(LatestGoodsEntity(goodsId))
            if (isFull()) {
                deleteOldest()
            }
        }.join()
    }

    private fun isFull(): Boolean {
        val latestGoods = latestGoodsDao.getAll()
        return latestGoods.size > MAX_SIZE
    }

    private fun deleteOldest() {
        latestGoodsDao.deleteOldest()
    }

    override fun getAll(): List<LatestGoods> {
        var result: List<LatestGoods> = emptyList()

        thread {
            result = latestGoodsDao.getAll().map { it.toLatestGoods() }
        }.join()

        return result
    }

    companion object {
        private const val MAX_SIZE: Int = 10
    }
}
