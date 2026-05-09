package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface CartRepository {
    suspend fun getItems(): Cart

    suspend fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart

    suspend fun getTotalItemCount(): Int

    suspend fun getTotalQuantity(): Int

    suspend fun increaseQuantity(
        product: Product,
        quantity: Int,
    )

    @OptIn(ExperimentalUuidApi::class)
    suspend fun decreaseQuantity(productId: Uuid)

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteProduct(productId: Uuid)
}
