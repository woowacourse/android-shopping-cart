package woowacourse.shopping.data.repository

import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.fixture.getFixtureCartItems
import woowacourse.shopping.fixture.getFixtureCartedProducts
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.min

class FakeCartRepository : CartRepository {
    private var cartedItems: List<CartedProduct> = emptyList()
    private val cartItems: List<CartItem>
        get() = getFixtureCartItems(cartedItems)

    private var id = cartItems.lastOrNull()?.id?.plus(1L) ?: -1

    override fun fetchCartItems(page: Int): List<CartedProduct> {
        val fromIndex = page * 5
        val toIndex = min(fromIndex + 5, cartItems.size)
        if (fromIndex > toIndex) return emptyList()
        return cartedItems.subList(fromIndex, toIndex)
    }

    fun addAll(newItems: List<CartedProduct>) {
        cartedItems = cartedItems + newItems
    }

    override fun addCartItem(cartItem: CartItem) {
        cartedItems = cartedItems +
            CartedProduct(
                cartItem.copy(id = id++),
                Product(
                    cartItem.productId,
                    "사과${cartItem.productId + 1}",
                    "image${cartItem.productId + 1}",
                    1000 * (cartItem.productId.toInt() + 1),
                ),
            )
    }

    override fun fetchTotalCount(): Int {
        return cartItems.sumOf { it.quantity }
    }

    private fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    ) {
        val index = cartedItems.indexOfFirst { it.product.id == cartItemId }
        val existingItem = if (index == -1) null else cartedItems[index]
        if (existingItem != null) {
            cartedItems = cartedItems.mapIndexed { currIdx, cartedProduct ->
                if (currIdx == index) {
                    existingItem.copy(cartItem = existingItem.cartItem.copy(quantity = quantity))
                } else {
                    cartedProduct
                }
            }
            return
        }
        cartedItems = cartedItems.filterIndexed { currIndex, _ -> currIndex != index }
    }

    private fun removeCartItem(cartItem: CartItem) {
        cartedItems.filter { cartedProduct -> cartedProduct.cartItem.productId == cartItem.productId }
    }

    override fun removeAll() {
        cartedItems = emptyList()
    }

    override fun patchQuantity(productId: Long, quantity: Int, cartItem: CartItem?) {
        if (quantity == 0) {
            if (cartItem != null) {
                removeCartItem(cartItem)
            }
        } else {
            if (cartItem?.id != null) {
                updateQuantity(cartItem.id!!, quantity)
            } else {
                addCartItem(CartItem(productId = productId, quantity = quantity))
            }
        }
    }
}
