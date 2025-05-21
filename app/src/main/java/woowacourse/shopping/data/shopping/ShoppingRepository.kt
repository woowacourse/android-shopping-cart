package woowacourse.shopping.data.shopping

import woowacourse.shopping.domain.model.ShoppingGoods

interface ShoppingRepository {
    fun getAllGoods(): Set<ShoppingGoods>

    fun addItemsWithCount(
        id: Int,
        count: Int,
    )

    fun increaseItemCount(id: Int)

    fun removeItem(id: Int)

    fun decreaseItemCount(id: Int)

    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<ShoppingGoods>
}
