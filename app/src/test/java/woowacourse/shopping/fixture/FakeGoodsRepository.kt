package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.goods.Goods
import woowacourse.shopping.domain.repository.GoodsRepository

class FakeGoodsRepository(
    private val goodsData: List<List<Goods>> =
        listOf(
            List(20) { Goods.of(it.toLong(), "Item $it", 1000, "Test") },
            List(20) { Goods.of(it.toLong(), "Item ${it + 20}", 2000, "Test") },
        ),
) : GoodsRepository {
    override fun getGoodsById(id: Long): Goods {
        return goodsData.flatten().find { it.id == id } ?: SUNDAE.goods
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        return goodsData.getOrNull(page) ?: emptyList()
    }
}
