package woowacourse.shopping.data

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartPage
import woowacourse.shopping.model.Product

object CartRepository {
    private var cart: Cart = Cart()

    fun addItem(product: Product): Boolean {
        try {
            cart = cart.addItem(product)
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    fun deleteItem(id: String) {
        cart = cart.deleteItem(id)
    }

    fun getCartPage(
        page: Int,
        pageSize: Int,
    ): CartPage =
        cart.getPage(
            page = page,
            pageSize = pageSize,
        )

    fun getCartSize(): Int = cart.getTotalSize()
}
