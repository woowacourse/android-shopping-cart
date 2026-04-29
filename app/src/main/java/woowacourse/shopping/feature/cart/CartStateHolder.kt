package woowacourse.shopping.feature.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.feature.cart.model.CartInfo
import woowacourse.shopping.feature.cart.model.toUiModel

class CartStateHolder(private val cartRepository: CartRepository) {
    var cartItems: ImmutableList<CartInfo> by mutableStateOf(emptyList<CartInfo>().toImmutableList())
        private set

    init {
        cartItems = cartRepository.getCartItems().toUiModel()
    }

    fun removeFromCart(productId: String) {
        val updated = cartRepository.getCartItems().remove(productId)
        cartRepository.saveCartItems(updated)
        cartItems = updated.toUiModel()
    }
}

@Composable
fun retainCartStateHolder(): CartStateHolder = retain {
    CartStateHolder(CartRepositoryImpl)
}
