package woowacourse.shopping.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository

class CartScreenState(
    private val cartRepo: CartRepository,
    private val coroutineScope: CoroutineScope,
) {
    var cart: Cart by mutableStateOf(Cart(emptyMap()))
        private set

    init {
        coroutineScope.launch {
            cart = cartRepo.showAll()

        }
    }

    fun delete(item: Product) {
        coroutineScope.launch {
            cartRepo.delete(item)
            cart = cartRepo.showAll()
        }
    }
}
