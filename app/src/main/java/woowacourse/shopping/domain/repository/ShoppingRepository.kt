package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ShoppingGoods

interface ShoppingRepository {
    fun getAllGoods(
        onSuccess: (Set<ShoppingGoods>) -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun insertGoods(
        id: Int,
        quantity: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun removeGoods(
        id: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun increaseGoodsQuantity(
        id: Int,
        quantity: Int = QUANTITY_CHANGE_AMOUNT,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun decreaseGoodsQuantity(
        id: Int,
        quantity: Int = QUANTITY_CHANGE_AMOUNT,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun getPagedGoods(
        page: Int,
        count: Int,
        onSuccess: (List<ShoppingGoods>) -> Unit,
        onFailure: (String?) -> Unit,
    )

    companion object {
        private const val QUANTITY_CHANGE_AMOUNT: Int = 1
    }
}
