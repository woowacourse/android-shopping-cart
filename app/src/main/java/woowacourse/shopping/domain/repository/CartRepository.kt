package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Product

object CartRepository {
    private var cart: Cart = Cart()

    fun getCart(): Cart = cart

    fun getTotalCartSize(): Int = cart.size

    fun addItem(product: Product): AddItemResult {
        val addItemResult = cart.addItem(product)
        if (addItemResult is AddItemResult.NewAdded) cart = addItemResult.cart
        return addItemResult
    }

    fun deleteItem(id: String) {
        cart = cart.deleteItem(id)
    }
}
