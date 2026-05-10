package woowacourse.shopping.repository

import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem

interface ShoppingCartRepository {
    suspend fun increaseItemQuantityByProductId(productId: String, quantity: Quantity)

    suspend fun decreaseItemQuantityByProductId(
        productId: String,
        quantity: Quantity,
    )

    suspend fun removeItem(productId: String)

    suspend fun getItemByProductId(productId: String): ShoppingCartItem?

    suspend fun getItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem>

    suspend fun getTotalQuantity(): Int


    suspend fun getTotalSize(): Int
}
