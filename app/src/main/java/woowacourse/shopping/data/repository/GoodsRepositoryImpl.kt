package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.GoodsDao
import woowacourse.shopping.data.entity.toGoods
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository
import kotlin.concurrent.thread

class GoodsRepositoryImpl(
    private val goodsDao: GoodsDao,
) : GoodsRepository {
    override fun getById(id: Int): Goods? {
        var result: Goods? = null

        thread {
            result = goodsDao.getGoodsById(id).toGoods()
        }.join()

        return result
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        var result: List<Goods> = emptyList()

        thread {
            result = goodsDao.getPagedGoods(page, count).map { it.toGoods() }
        }.join()

        return result
    }
}
