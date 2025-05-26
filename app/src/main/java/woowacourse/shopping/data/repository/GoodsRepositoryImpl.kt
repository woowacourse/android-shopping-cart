package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.toGoods
import woowacourse.shopping.data.service.GoodsService
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository
import kotlin.concurrent.thread

class GoodsRepositoryImpl(
    private val goodsService: GoodsService,
) : GoodsRepository {
    override fun getById(
        id: Int,
        onSuccess: (Goods?) -> Unit,
    ) {
        thread {
            onSuccess(goodsService.getGoodsById(id)?.toGoods())
        }
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
        onSuccess: (List<Goods>) -> Unit,
    ) {
        thread {
            onSuccess(
                goodsService.getPagedGoods(page, count).map {
                    it.toGoods() ?: throw Exception()
                },
            )
        }
    }

    override fun getGoodsListByIds(
        ids: List<Int>,
        onSuccess: (List<Goods>) -> Unit,
    ) {
        thread {
            onSuccess(
                goodsService.getGoodsListByIds(ids).map {
                    it.toGoods() ?: throw Exception()
                },
            )
        }
    }
}
