package woowacourse.shopping.ui.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var cart: Cart by mutableStateOf(Cart(emptyMap()))
        private set

    init {
        if (cart.items.isEmpty()) {
            isLoading = true

            coroutineScope.launch {
                try {
                    cart = cartRepo.showAll()
                } catch (e: Exception) {

                } finally {
                    isLoading = false
                }
            }
        }
    }

    fun delete(item: Product) {
        isLoading = true

        coroutineScope.launch {
            try {
                cartRepo.delete(item)
                cart = cartRepo.showAll()
            } finally {
                isLoading = false
            }
        }
    }
}

@Composable
fun rememberCartScreenState(
    cartRepo: CartRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): CartScreenState {
    return remember {
        CartScreenState(
            cartRepo = cartRepo,
            coroutineScope = coroutineScope,
            //initialCart = Cart(emptyMap())
        )
    }
}
