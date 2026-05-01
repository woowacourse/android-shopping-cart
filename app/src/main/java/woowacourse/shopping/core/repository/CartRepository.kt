package woowacourse.shopping.core.repository

import woowacourse.shopping.core.model.Product
import woowacourse.shopping.feature.cart.model.AddItemResult
import woowacourse.shopping.feature.cart.model.Cart

object CartRepository {
    private var cart: Cart = Cart()

    fun getCart(): Cart = cart

    fun addItem(product: Product): AddItemResult {
        val addItemResult = cart.addItem(product)
        if (addItemResult is AddItemResult.NewAdded) cart = addItemResult.cart
        return addItemResult
    }

    fun deleteItem(id: String) {
        cart = cart.deleteItem(id)
    }
}
