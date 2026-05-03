package woowacourse.shopping

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product

object CartProvider : CartRepository {
    var cart = Cart(CartProducts(emptyList()))

    override fun addItem(product: Product) {
        cart = cart.addProduct(product)
    }

    override fun removeItem(product: Product) {
        cart = cart.removeProduct(product.uuid)
    }
}
