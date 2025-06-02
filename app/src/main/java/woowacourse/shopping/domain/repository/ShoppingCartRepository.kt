package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ShoppingCartItem

interface ShoppingCartRepository {
    fun saveItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    )

    fun addOrIncreaseQuantity(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    )

    fun removeItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    )

    fun getItem(
        id: Long,
        onResult: (Result<ShoppingCartItem?>) -> Unit,
    )

    fun getAllItems(onResult: (Result<List<ShoppingCartItem>>) -> Unit)

    fun getPagedItems(
        page: Int,
        count: Int,
        onResult: (Result<List<ShoppingCartItem>>) -> Unit,
    )

    fun getTotalQuantity(onResult: (Result<Int>) -> Unit)
}
