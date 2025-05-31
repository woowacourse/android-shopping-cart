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
    override fun insertLatestGoods(
        goodsId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                latestGoodsDao.insert(LatestGoodsEntity(goodsId))
                if (isFull()) {
                    deleteOldest()
                }
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    private fun isFull(): Boolean {
        val count = latestGoodsDao.getCount()
        return count > MAX_SIZE
    }

    private fun deleteOldest() {
        latestGoodsDao.deleteOldest()
    }

    override fun getAll(
        onSuccess: (List<LatestGoods>) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                onSuccess(latestGoodsDao.getAll().map { it.toLatestGoods() })
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun getLast(
        onSuccess: (LatestGoods?) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        thread {
            try {
                onSuccess(latestGoodsDao.getLast()?.toLatestGoods())
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    companion object {
        private const val MAX_SIZE: Int = 10
    }
}
