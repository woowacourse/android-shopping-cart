package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.cart.Cart

interface CartRepository {
    suspend fun getItems(): Cart

    suspend fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart

    suspend fun getTotalItemCount(): Int

    suspend fun getTotalQuantity(): Int

    suspend fun increaseQuantity(
        productId: Int,
        quantity: Int,
    )

    suspend fun decreaseQuantity(productId: Int)

    suspend fun deleteProduct(productId: Int)
}
