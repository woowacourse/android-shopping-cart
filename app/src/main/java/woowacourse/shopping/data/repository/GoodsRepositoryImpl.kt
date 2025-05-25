package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.toGoods
import woowacourse.shopping.data.service.GoodsService
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository
import kotlin.concurrent.thread

class GoodsRepositoryImpl(
    private val goodsService: GoodsService,
) : GoodsRepository {
    override fun getById(id: Int): Goods? {
        var result: Goods? = null

        thread {
            result = goodsService.getGoodsById(id)?.toGoods()
        }.join()

        return result
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        var result: List<Goods> = emptyList()

        thread {
            result = goodsService.getPagedGoods(page, count).mapNotNull { it.toGoods() }
        }.join()

        return result
    }
}
