package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartEntry

interface CartRepository {
    fun insert(
        item: Cart,
        onResult: () -> Unit = {},
    )

    fun getById(
        id: Long,
        onResult: (CartEntry?) -> Unit,
    )

    fun getByIds(
        ids: List<Long>,
        onResult: (List<CartEntry>) -> Unit,
    )

    fun getAll(onResult: (List<CartEntry>) -> Unit)

    fun totalSize(onResult: (Int) -> Unit)

    fun update(
        item: Cart,
        onResult: () -> Unit,
    )

    fun deleteById(
        id: Long,
        onResult: (Unit) -> Unit,
    )

    fun getPaged(
        offset: Int,
        limit: Int,
        onResult: (List<CartEntry>) -> Unit,
    )

    fun hasOnlyPage(
        limit: Int,
        onResult: (Boolean) -> Unit,
    )

    fun hasNextPage(
        nextOffset: Int,
        limit: Int,
        onResult: (Boolean) -> Unit,
    )
}
