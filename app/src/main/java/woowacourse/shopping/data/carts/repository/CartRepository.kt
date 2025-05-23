package woowacourse.shopping.data.carts.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods

interface CartRepository {
    fun getAll(): List<CartItem>

    fun fetchAllCartItems(onComplete: (List<CartItem>) -> Unit)

    fun fetchPageCartItems(
        limit: Int,
        offset: Int,
        onComplete: (List<CartItem>) -> Unit,
    )

    fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    )

    fun addOrIncreaseQuantity(
        goods: Goods,
        addQuantity: Int,
        onComplete: () -> Unit,
    )

    fun removeOrDecreaseQuantity(
        goods: Goods,
        removeQuantity: Int,
        onComplete: () -> Unit,
    )

    fun delete(
        goods: Goods,
        onComplete: () -> Unit,
    )

    fun getPage(
        limit: Int,
        offset: Int,
    ): List<CartItem>

    fun getAllItemsSize(): Int
}
