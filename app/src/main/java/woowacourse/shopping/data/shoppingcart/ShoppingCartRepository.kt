package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.domain.ShoppingProduct

interface ShoppingCartRepository {
    fun insert(
        productId: Long,
        quantity: Int,
        onLoad: (Result<Unit>) -> Unit,
    )

    fun get(
        productId: Long,
        onLoad: (Result<ShoppingProduct?>) -> Unit,
    )

    fun getAll(onLoad: (Result<List<ShoppingProduct>?>) -> Unit)

    fun getAllSize(onLoad: (Result<Int>) -> Unit)

    fun increaseProduct(
        productId: Long,
        onLoad: (Result<Unit>) -> Unit,
    )

    fun decreaseProduct(
        productId: Long,
        onLoad: (Result<Unit>) -> Unit,
    )

    fun getQuantity(
        productId: Long,
        onLoad: (Result<Int>) -> Unit,
    )

    fun getPaged(
        limit: Int,
        offset: Int,
        onLoad: (Result<List<ShoppingProduct>>) -> Unit,
    )

    fun delete(
        productId: Long,
        onLoad: (Result<Unit>) -> Unit,
    )
}
