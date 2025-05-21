package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ShoppingGoods

interface ShoppingRepository {
    fun getAllGoods(): Set<ShoppingGoods>

    fun increaseItemQuantity(
        id: Int,
        quantity: Int = QUANTITY_CHANGE_AMOUNT,
    )

    fun decreaseItemQuantity(
        id: Int,
        quantity: Int = -QUANTITY_CHANGE_AMOUNT,
    )

    fun removeItem(id: Int)

    fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<ShoppingGoods>

    companion object {
        private const val QUANTITY_CHANGE_AMOUNT: Int = 1
    }
}
