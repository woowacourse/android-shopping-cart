package woowacourse.shopping.ui.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository

private const val PAGE_SIZE = 5

class CartScreenState(
    private val cartRepo: CartRepository,
    private val coroutineScope: CoroutineScope,
    initialPage: Int
) {
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var cart: Cart by mutableStateOf(Cart(emptyList()))
        private set

    var currentPage: Int by mutableIntStateOf(initialPage)
        private set

    val pagedItems: List<CartItem>
        get() = cart.getPagedItems(
            fromIndex = (currentPage - 1) * PAGE_SIZE,
            pageSize = PAGE_SIZE
        )

    val totalPages: Int
        get() = (cart.items.size - 1) / PAGE_SIZE + 1
    val showPagination: Boolean
        get() = cart.items.size >= PAGE_SIZE + 1

    init {
        if (cart.items.isEmpty()) {
            isLoading = true

            coroutineScope.launch {
                try {
                    cart = cartRepo.showAll()
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

    fun nextPage() {
        currentPage = (currentPage + 1).coerceAtMost(totalPages)
    }

    fun previousPage() {
        currentPage = (currentPage - 1).coerceAtLeast(1)
    }

    companion object {
        fun Saver(
            cartRepo: CartRepository,
            coroutineScope: CoroutineScope,
        ): Saver<CartScreenState, Int> =
            Saver(
                save = { state ->
                    state.currentPage
                },
                restore = { savedPage ->
                    CartScreenState(
                        cartRepo = cartRepo,
                        coroutineScope = coroutineScope,
                        initialPage = savedPage
                    )
                }
            )
    }
}

@Composable
fun rememberCartScreenState(
    cartRepo: CartRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): CartScreenState {
    val saver = CartScreenState.Saver(cartRepo, coroutineScope)
    return rememberSaveable(saver = saver) {
        CartScreenState(
            cartRepo = cartRepo,
            coroutineScope = coroutineScope,
            initialPage = 1,
        )
    }
}
