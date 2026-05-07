package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartRepositoryImpl(
    private var cart: Cart,
) : CartRepository {
    override fun getItems(): Cart = cart

    override fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart {
        if (page < 0 || pageSize <= 0) return Cart()

        val fromIndex = page * pageSize

        if (fromIndex >= getTotalItemCount()) {
            return Cart()
        }

        val toIndex = min(fromIndex + pageSize, cart.cartItems.size)
        return Cart(cart.cartItems.subList(fromIndex, toIndex))
    }

    override fun getTotalItemCount(): Int = cart.cartItems.size

    override fun getTotalQuantity(): Int = cart.getTotalQuantity()

    override fun increaseQuantity(
        product: Product,
        amount: Int,
    ) {
        cart = cart.increaseQuantity(product, amount)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun decreaseQuantity(productId: Uuid) {
        cart = cart.decreaseQuantity(productId)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProduct(productId)
    }
}
