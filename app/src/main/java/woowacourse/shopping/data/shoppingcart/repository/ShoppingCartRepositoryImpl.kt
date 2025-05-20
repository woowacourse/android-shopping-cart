package woowacourse.shopping.data.shoppingcart.repository

import woowacourse.shopping.data.shoppingcart.ShoppingDataBase
import woowacourse.shopping.domain.model.Goods

class ShoppingCartRepositoryImpl(
    private val database: ShoppingDataBase = ShoppingDataBase,
) : ShoppingCartRepository {
    override fun addGoods(goods: Goods) {
        database.addItem(goods)
    }

    override fun removeGoods(goods: Goods) {
        database.removeItem(goods)
    }

    override fun getGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        return database.getPagedGoods(page, count)
    }
}
