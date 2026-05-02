package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val CART_PAGE_SIZE = 5

object CartRepositoryImpl : CartRepository {
    private var cart = Cart()

    override fun getItems(): Cart = cart

    override fun getPagingItems(page: Int): Cart {
        val fromIndex = page * CART_PAGE_SIZE
        val toIndex = min(fromIndex + CART_PAGE_SIZE, cart.cartItems.size)
        return Cart(cart.cartItems.subList(fromIndex, toIndex))
    }

    override fun addProduct(product: Product) {
        cart = cart.addProductToCart(product)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProductFromCart(productId)
    }
}
