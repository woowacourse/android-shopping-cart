package woowacourse.shopping.data

import woowacourse.shopping.data.cart.CartEntity
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.Product

class CartProductDataAdapter(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) {
    fun getCartProducts(): List<CartProduct> {
        val cartEntities = cartRepository.getCartEntities()
        val productItems = cartEntities.map { cart ->
            cartToProductItem(cart)
        }
        return productItems
    }

    private fun cartToProductItem(cart: CartEntity): CartProduct {
        val cartProduct =
            productRepository.findProductById(cart.productId) ?: Product.defaultProduct
        return CartProduct(cartProduct, cart.count, true)
    }
}
