package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface CartRepository {
    fun getItems(): Cart

    fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart

    fun getTotalItemCount(): Int

    fun getTotalQuantity(): Int

    fun getTotalPrice(): Int

    fun increaseQuantity(
        product: Product,
        amount: Int,
    )

    @OptIn(ExperimentalUuidApi::class)
    fun decreaseQuantity(productId: Uuid)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProduct(productId: Uuid)
}
