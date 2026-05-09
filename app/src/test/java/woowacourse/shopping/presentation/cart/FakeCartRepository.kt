package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class FakeCartRepository(
    private var cart: Cart = Cart(),
) : CartRepository {
    override suspend fun getItems(): Cart = cart

    override suspend fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart {
        if (page < 0 || pageSize <= 0) return Cart()

        val fromIndex = page * pageSize
        if (fromIndex >= cart.cartItems.size) return Cart()

        val toIndex = minOf(fromIndex + pageSize, cart.cartItems.size)
        return Cart(cart.cartItems.subList(fromIndex, toIndex))
    }

    override suspend fun getTotalItemCount(): Int = cart.cartItems.size

    override suspend fun getTotalQuantity(): Int = cart.getTotalQuantity()

    override suspend fun increaseQuantity(
        product: Product,
        quantity: Int,
    ) {
        cart = cart.increaseQuantity(product, quantity)
    }

    override suspend fun decreaseQuantity(productId: Uuid) {
        cart = cart.decreaseQuantity(productId)
    }

    override suspend fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProduct(productId)
    }
}
