package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getCartItemCount(onResult: (Result<Int?>) -> Unit)

    fun getTotalQuantity(onResult: (Result<Int?>) -> Unit)

    fun insertProduct(
        cartItem: CartItem,
        onResult: (Result<Unit>) -> Unit,
    )

    fun insertOrIncrease(
        product: Product,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun increaseQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteProduct(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )
}
