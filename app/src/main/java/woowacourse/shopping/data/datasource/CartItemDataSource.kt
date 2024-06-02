package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.entity.ShoppingCartItemEntity

interface CartItemDataSource {
    fun insert(shoppingCartItem: ShoppingCartItemEntity)

    fun insertAll(shoppingCartItems: List<ShoppingCartItemEntity>)

    fun increaseCount(
        productId: Long,
        quantity: Int,
    )

    fun decreaseCount(
        productId: Long,
        quantity: Int,
    )

    fun delete(productId: Long)

    fun shoppingCartItems(): List<ShoppingCartItemEntity>

    fun shoppingCartItems(
        pageSize: Int,
        pageIndex: Int,
    ): List<ShoppingCartItemEntity>

    fun itemCount(): Int

    fun cartItemById(productId: Long): ShoppingCartItemEntity?

    fun updateTotalQuantity(
        productId: Long,
        newQuantity: Int,
    )
}
