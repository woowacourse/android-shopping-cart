package woowacourse.shopping.repository

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product
import java.util.UUID

object InMemoryCartRepository : CartRepository {
    override var cart: Cart = Cart(CartProducts(emptyList()))
        private set

    override suspend fun addProduct(
        product: Product,
        amount: Int,
    ) {
        cart = cart.addProduct(product, amount)
    }

    override suspend fun decreaseProduct(productId: UUID, amount: Int) {
        cart = cart.decreaseProduct(productId, amount)
    }

    override suspend fun removeProduct(productId: UUID) {
        cart = cart.removeProduct(productId)
    }
}
