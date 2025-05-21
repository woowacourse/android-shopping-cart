package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.data.goods.GoodsDataBase
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository

class GoodsRepositoryImpl(
    private val database: GoodsDataBase = GoodsDataBase,
) : GoodsRepository {
    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        return database.getPagedGoods(page, count)
    }
}
