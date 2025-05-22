package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.LatestGoodsDao
import woowacourse.shopping.data.entity.LatestGoodsEntity
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import kotlin.concurrent.thread

class LatestGoodsRepositoryImpl(
    private val latestGoodsDao: LatestGoodsDao,
) : LatestGoodsRepository {
    override fun insertLatestGoods(goodsId: Int) {
        thread {
            latestGoodsDao.insert(LatestGoodsEntity(goodsId))
        }.join()
    }
}
