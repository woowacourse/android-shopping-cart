package woowacourse.shopping.data

import woowacourse.shopping.domain.model.CartGoods
import woowacourse.shopping.domain.model.Goods

interface ShoppingRepository {
    fun getAllGoods(): Set<CartGoods>

    fun addItemsWithCount(
        goods: Goods,
        count: Int,
    )

    fun increaseItemCount(goods: Goods)

    fun removeItem(id: Int)

    fun decreaseItemCount(id: Int)

    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<CartGoods>
}
