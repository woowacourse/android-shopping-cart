package woowacourse.shopping.data.repository

import android.util.Log
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addToCart(item: CartItem) {
        TODO("Not yet implemented")
    }

    override fun removeFromCart(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun increaseQuantity(productId: Long): Result<Unit> =
        runCatching {
            cartDataSource.increaseQuantity(productId)
        }.onFailure { e ->
            Log.e("CartRepository", "increaseQuantity failed", e)
        }

    override fun decreaseQuantity(productId: Long): Result<Unit> =
        runCatching {
            cartDataSource.increaseQuantity(productId)
        }
}
