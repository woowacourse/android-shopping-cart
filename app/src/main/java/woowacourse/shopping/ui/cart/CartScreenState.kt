package woowacourse.shopping.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository

class CartScreenState(private val cartRepo: CartRepository) {
    var cart: Cart by mutableStateOf(cartRepo.showAll())
        private set

    fun delete(item: Product) {
        cartRepo.delete(item)
        cart = cartRepo.showAll()
    }
}
