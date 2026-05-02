package woowacourse.shopping.data

import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository

object InMemoryCartRepository : CartRepository {
    private var cart: Cart = Cart()

    override suspend fun getCart(): Cart = cart

    override suspend fun getTotalCartSize(): Int = cart.size

    override suspend fun addItem(product: Product): AddItemResult {
        val result = cart.addItem(product)
        if (result is AddItemResult.NewAdded) cart = result.cart
        return result
    }

    override suspend fun deleteItem(id: String) {
        cart = cart.deleteItem(id)
    }
}
