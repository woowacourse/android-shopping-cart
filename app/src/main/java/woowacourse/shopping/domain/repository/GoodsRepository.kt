package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Goods

interface GoodsRepository {
    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods>
}
