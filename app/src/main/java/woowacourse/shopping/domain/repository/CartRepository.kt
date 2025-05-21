package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getCartItemCount(onResult: (Result<Int?>) -> Unit)

    fun getTotalQuantity(onResult: (Result<Int?>) -> Unit)

    fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit)

    fun getCartItemById(
        productId: Long,
        onResult: (Result<CartEntity?>) -> Unit,
    )

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
