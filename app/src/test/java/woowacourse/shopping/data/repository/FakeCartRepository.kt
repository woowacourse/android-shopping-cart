package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class FakeCartRepository(
    private var cart: Cart = Cart(),
    private val productRepository: ProductRepository = FakeProductRepository(),
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
        productId: Int,
        quantity: Int,
    ) {
        val cartItem = cart.cartItems.find { it.product.productId == productId }

        val product =
            cartItem?.product
                ?: productRepository.findProductById(productId) ?: return

        cart = cart.increaseQuantity(product, quantity)
    }

    override suspend fun decreaseQuantity(productId: Int) {
        cart = cart.decreaseQuantity(productId)
    }

    override suspend fun deleteProduct(productId: Int) {
        cart = cart.deleteProduct(productId)
    }
}
