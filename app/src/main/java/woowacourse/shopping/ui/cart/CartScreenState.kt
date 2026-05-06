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
    var currentPage: Int by mutableIntStateOf(initialPage)
        private set
    var pagedItems: List<CartItem> by mutableStateOf(emptyList())
        private set
    var totalItemCount: Int by mutableIntStateOf(0)
        private set
    val totalPages: Int
        get() = (totalItemCount - 1) / PAGE_SIZE + 1
    val showPagination: Boolean
        get() = totalItemCount >= PAGE_SIZE + 1

    init {
        if (pagedItems.isEmpty()) {
            isLoading = true

            coroutineScope.launch {
                try {
                    loadData()
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
                loadData()
            } finally {
                isLoading = false
            }
        }
    }

    fun nextPage() {
        isLoading = true

        coroutineScope.launch {
            try {
                currentPage = (currentPage + 1).coerceAtMost(totalPages)
                loadData()
            } finally {
                isLoading = false
            }
        }
    }

    fun previousPage() {
        isLoading = true

        coroutineScope.launch {
            try {
                currentPage = (currentPage - 1).coerceAtLeast(1)
                loadData()
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun loadData() {
        val newTotalCount = cartRepo.getSize()

        val validMaxPage = ((newTotalCount - 1) / PAGE_SIZE + 1).coerceAtLeast(1)

        if (currentPage > validMaxPage) currentPage = validMaxPage

        pagedItems = cartRepo.getPagedItems(
            fromIndex = (currentPage - 1) * PAGE_SIZE,
            count = PAGE_SIZE
        )
        totalItemCount = newTotalCount
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
