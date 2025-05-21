package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods

interface CartRepository {
    fun getAll(): List<Goods>

    fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    )

    fun insertOrAddQuantity(
        goods: Goods,
        addQuantity: Int,
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
