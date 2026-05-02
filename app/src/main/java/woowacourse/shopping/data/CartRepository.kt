package woowacourse.shopping.data

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product

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

    fun deleteItem(id: String) {
        cart = cart.deleteItem(id)
    }
}
