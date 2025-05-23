package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ShoppingCartItem

interface ShoppingCartRepository {
    fun addItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    )

    fun addItems(
        items: List<ShoppingCartItem>,
        onResult: (Result<Unit>) -> Unit,
    )

    fun removeItem(
        shoppingCartItem: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit,
    )

    fun getPagedItems(
        page: Int,
        count: Int,
        onResult: (Result<List<ShoppingCartItem>>) -> Unit,
    )

    fun getTotalQuantity(onResult: (Result<Int>) -> Unit)
}
