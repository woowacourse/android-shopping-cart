package woowacourse.shopping.presentation.goods

import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.domain.model.Goods

class FakeGoodsRepository(
    private val goodsData: List<List<Goods>> =
        listOf(
            List(20) { Goods.of("Item $it", 1000, "Test") }, // page 0
            List(20) { Goods.of("Item ${it + 20}", 2000, "Test") }, // page 1
        ),
) : GoodsRepository {
    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        return goodsData.getOrNull(page) ?: emptyList()
    }
}
