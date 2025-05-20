package woowacourse.shopping.data.shoppingcart.repository

import woowacourse.shopping.domain.model.Goods

interface ShoppingCartRepository {
    fun addGoods(goods: Goods)

    fun removeGoods(goods: Goods)

    fun getGoods(
        page: Int,
        count: Int,
    ): List<Goods>
}
