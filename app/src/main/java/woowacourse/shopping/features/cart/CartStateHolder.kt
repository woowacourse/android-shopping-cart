package woowacourse.shopping.features.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.cart.CartRepositoryMockImpl
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.repository.CartRepository
import kotlin.math.ceil
import kotlin.math.max

class CartStateHolder(
    private val cartRepository: CartRepository,
) {
    var pageCartItems by mutableStateOf(emptyList<CartItem>())
    var totalPages by mutableStateOf(0)
    var currentPage by mutableStateOf(0)

    init {
        loadCartPage()
    }

    fun isFirstPage(): Boolean = currentPage == 0

    fun isLastPage(): Boolean = currentPage == totalPages - 1 || totalPages == 0

    fun loadCartPage() {
        totalPages = ceil(cartRepository.getTotalCartCount().toDouble() / PAGE_SIZE).toInt()
        if (currentPage >= totalPages) {
            currentPage = max(0, totalPages - 1)
        }
        val cart = cartRepository.getCart()
        pageCartItems = cart.getPage(currentPage, PAGE_SIZE)
    }

    fun removeCartItem(cartItem: CartItem) {
        cartRepository.removeCartItem(cartItem)
        loadCartPage()
    }

    fun goToNextPage() {
        if (isLastPage()) return
        currentPage += 1
        loadCartPage()
    }

    fun goToPreviousPage() {
        if (isFirstPage()) return
        currentPage -= 1
        loadCartPage()
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}

@Composable
fun retainCartStateHolder(): CartStateHolder =
    retain {
        CartStateHolder(CartRepositoryMockImpl)
    }
