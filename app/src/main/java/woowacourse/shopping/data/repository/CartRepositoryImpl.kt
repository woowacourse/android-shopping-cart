package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object CartRepositoryImpl : CartRepository {
    private var cart = Cart()

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

    override fun addProduct(product: Product) {
        cart = cart.addProductToCart(product)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProductFromCart(productId)
    }
}
