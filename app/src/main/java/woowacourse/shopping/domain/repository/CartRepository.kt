package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit)

    fun addToCart(item: CartItem)

    fun removeFromCart(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    )

    fun existsByProductId(
        productId: Long,
        onResult: (Result<Boolean>) -> Unit,
    )

    fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun insertOrIncrease(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun increaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    )
}
