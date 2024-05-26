package woowacourse.shopping.presentation.fake

import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.fixture.getFixtureCartItems
import woowacourse.shopping.fixture.getFixtureCartedProducts
import java.io.Serializable
import kotlin.math.min

class FakeCartRepository : CartRepository {
    private val cartedItems: MutableList<CartedProduct> =
        getFixtureCartedProducts(100).toMutableList()
    private val cartItems: List<CartItem>
        get() = getFixtureCartItems(cartedItems)

    private var id = cartItems.last().id?.plus(1L) ?: -1

    override fun fetchCartItems(page: Int): List<CartedProduct> {
        val fromIndex = page * 5
        val toIndex = min(fromIndex + 5, cartItems.size)
        return cartedItems.subList(fromIndex, toIndex)
    }

    override fun addCartItem(cartItem: CartItem) {
        cartedItems.add(
            CartedProduct(
                cartItem.copy(id = id++),
                Product(
                    cartItem.productId,
                    "사과${cartItem.productId + 1}",
                    "image${cartItem.productId + 1}",
                    1000 * (cartItem.productId.toInt() + 1),
                ),
            ),
        )
    }

    override fun fetchTotalCount(): Int {
        return cartItems.sumOf { it.quantity }
    }

    override fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    ) {
        val index = cartedItems.indexOfFirst { it.product.id == cartItemId }
        val existingItem = if (index == -1) null else cartedItems[index]
        if (existingItem != null) {
            cartedItems[index] =
                existingItem.copy(cartItem = existingItem.cartItem.copy(quantity = quantity))
            return
        }
        cartedItems.removeAt(index)
    }

    override fun removeCartItem(cartItem: CartItem) {
        cartedItems.removeIf { it.cartItem.productId == cartItem.productId }
    }

    override fun removeAll() {
        cartedItems.clear()
    }
}
