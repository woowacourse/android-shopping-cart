package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Cart

interface CartRepository {
    fun insert(
        item: Cart,
        onResult: () -> Unit = {},
    )

    fun getById(
        id: Long,
        onResult: (Cart?) -> Unit,
    )

    fun getPagedShopItems(
        offset: Int,
        limit: Int,
        onResult: (List<Cart>) -> Unit,
    )

    fun getAll(onResult: (List<Cart>) -> Unit)

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
        onResult: (List<Cart>) -> Unit,
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
