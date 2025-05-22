package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartEntry

interface CartRepository {
    fun insert(
        item: CartEntry,
        onResult: (Unit) -> Unit = {},
    )

    fun getByIds(
        ids: List<Long>,
        onResult: (List<CartEntry>) -> Unit,
    )

    fun getAll(onResult: (List<CartEntry>) -> Unit)

    fun totalSize(onResult: (Int) -> Unit)

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
