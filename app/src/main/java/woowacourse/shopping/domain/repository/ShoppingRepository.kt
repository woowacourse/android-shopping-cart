package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ShoppingGoods

interface ShoppingRepository {
    fun getAllGoods(onSuccess: (Set<ShoppingGoods>) -> Unit)

    fun insertGoods(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
    )

    fun removeGoods(
        id: Int,
        onSuccess: () -> Unit,
    )

    fun increaseGoodsQuantity(
        id: Int,
        quantity: Int = QUANTITY_CHANGE_AMOUNT,
        onSuccess: () -> Unit,
    )

    fun decreaseGoodsQuantity(
        id: Int,
        quantity: Int = QUANTITY_CHANGE_AMOUNT,
        onSuccess: () -> Unit,
    )

    fun getPagedGoods(
        page: Int,
        count: Int,
        onSuccess: (List<ShoppingGoods>) -> Unit,
    )

    companion object {
        private const val QUANTITY_CHANGE_AMOUNT: Int = 1
    }
}
