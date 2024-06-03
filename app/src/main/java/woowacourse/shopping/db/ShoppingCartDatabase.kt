package woowacourse.shopping.db

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import woowacourse.shopping.db.cartItem.CartItemEntity
import woowacourse.shopping.model.CartItem

object ShoppingCartDatabase {
    private lateinit var database: CartDatabase

    fun initialize(context: Context) {
        database = CartDatabase.getDatabase(context)
    }

    fun getDatabase(): CartDatabase {
        return database
    }

    suspend fun getCartItems(): List<CartItem> =
        withContext(Dispatchers.IO) {
            database.cartItemDao().getAllCartItems().map { it.toCartItem() }
        }

    suspend fun addProductToCart(productId: Int) =
        withContext(Dispatchers.IO) {
            val cartItem = database.cartItemDao().getCartItemByProductId(productId)
            if (cartItem != null) {
                cartItem.quantity++
                database.cartItemDao().update(cartItem)
            } else {
                database.cartItemDao().insert(CartItemEntity(productId = productId, quantity = 1))
            }
        }

    suspend fun deleteProduct(productId: Int) =
        withContext(Dispatchers.IO) {
            val cartItem = database.cartItemDao().getCartItemByProductId(productId)
            if (cartItem != null) {
                database.cartItemDao().delete(cartItem)
            }
        }

    suspend fun addProductCount(productId: Int) =
        withContext(Dispatchers.IO) {
            val cartItem = database.cartItemDao().getCartItemByProductId(productId)
            if (cartItem != null) {
                cartItem.quantity++
                database.cartItemDao().update(cartItem)
            }
        }

    suspend fun subtractProductCount(productId: Int) =
        withContext(Dispatchers.IO) {
            val cartItem = database.cartItemDao().getCartItemByProductId(productId)
            if (cartItem != null && cartItem.quantity > 1) {
                cartItem.quantity--
                database.cartItemDao().update(cartItem)
            } else if (cartItem != null && cartItem.quantity == 1) {
                database.cartItemDao().delete(cartItem)
            }
        }

    suspend fun getCartItemById(productId: Int): CartItem {
        return database.cartItemDao().getCartItemByProductId(productId)?.toCartItem() ?: CartItem(
            productId,
            0
        )
    }
}
