package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Goods

interface GoodsRepository {
    fun getGoodsById(id: Long): Goods

    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods>
}
