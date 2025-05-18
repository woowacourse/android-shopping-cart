package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods

interface GoodsRepository {
    fun getAllGoods(): List<Goods>

    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods>
}
