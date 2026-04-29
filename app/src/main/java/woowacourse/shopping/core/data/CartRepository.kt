package woowacourse.shopping.core.data

import woowacourse.shopping.core.model.Product
import woowacourse.shopping.feature.cart.model.Cart

object CartRepository {
    private var cart: Cart = Cart()

    fun getCart(): Cart = cart

    fun addItem(product: Product): Boolean {
        try {
            cart = cart.addItem(product)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun deleteItem(product: Product) {
        cart = cart.deleteItem(product)
    }
}
