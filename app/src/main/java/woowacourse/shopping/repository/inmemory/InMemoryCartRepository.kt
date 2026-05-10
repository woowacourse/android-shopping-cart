package woowacourse.shopping.repository.inmemory

import android.content.Context
import android.content.SharedPreferences
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.CartRepository
import java.util.UUID

object InMemoryCartRepository : CartRepository {
    private const val PREF_NAME = "shopping_cart"
    private const val KEY_CART_ITEMS = "cart_items"

    private lateinit var preferences: SharedPreferences
    private var cart = Cart(emptyList())

    fun initialize(context: Context) {
        if (::preferences.isInitialized) return

        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        cart = restoreCart()
    }

    override suspend fun add(item: ProductId) {
        ensureInitialized()
        cart = cart.add(item)
        persistCart()
    }

    override suspend fun delete(item: ProductId) {
        ensureInitialized()
        cart = cart.delete(item)
        persistCart()
    }

    override suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): List<CartItem> {
        ensureInitialized()

        val safeFrom = fromIndex.coerceIn(0, cart.items.size)
        val safeLimit = limit.coerceAtLeast(0)
        val safeTo = minOf(safeFrom + safeLimit, cart.items.size)

        return cart.items.subList(safeFrom, safeTo)
    }

    override suspend fun getCartItemsByProductIds(productIds: Set<ProductId>): List<CartItem> {
        ensureInitialized()
        return cart.items.filter { it.productId in productIds }
    }

    override suspend fun count(): Int {
        ensureInitialized()
        return cart.count()
    }

    private fun persistCart() {
        val encoded =
            cart.items.joinToString(",") { item ->
                "${item.productId.value}:${item.quantity}"
            }

        preferences
            .edit()
            .putString(KEY_CART_ITEMS, encoded)
            .commit()
    }

    private fun restoreCart(): Cart {
        val saved = preferences.getString(KEY_CART_ITEMS, null) ?: return Cart(emptyList())
        if (saved.isBlank()) return Cart(emptyList())

        return runCatching {
            val items =
                saved
                    .split(",")
                    .filter { it.isNotBlank() }
                    .map { token ->
                        val parts = token.split(":")
                        require(parts.size == 2)

                        val productId = ProductId(UUID.fromString(parts[0]))
                        val quantity = parts[1].toInt()

                        CartItem(
                            productId = productId,
                            quantity = quantity,
                        )
                    }

            Cart(items)
        }.getOrElse {
            Cart(emptyList())
        }
    }

    private fun ensureInitialized() {
        check(::preferences.isInitialized) {
            "InMemoryCartRepository.initialize(context)가 먼저 호출되어야 합니다."
        }
    }
}
