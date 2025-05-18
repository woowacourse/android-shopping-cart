package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods

interface ShoppingRepository {
    fun addItem(goods: Goods)

    fun removeItem(goods: Goods)

    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods>
}
