package woowacourse.shopping.ui.shopping

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
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository

private const val LOAD_SIZE = 20

class ShoppingScreenState(
    private val productRepo: ProductRepository,
    private val coroutineScope: CoroutineScope,
    initialVisibleCount: Int,
) {
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var visibleCount: Int by mutableIntStateOf(initialVisibleCount)
        private set
    var visibleProducts: List<Product> by mutableStateOf(emptyList())
        private set
    var hasNext: Boolean by mutableStateOf(false)
        private set
    var sizeInRepo: Int by mutableIntStateOf(0)
        private set

    init {
        if (visibleProducts.isEmpty()) {
            isLoading = true
            coroutineScope.launch {
                try {
                    visibleProducts = productRepo.getProducts(0, visibleCount)
                    hasNext = productRepo.hasNext(visibleProducts.count() - 1)
                    sizeInRepo = productRepo.getSize()
                } finally {
                    isLoading = false
                }
            }
        }
    }

    fun loadMore() {
        visibleCount = minOf(visibleCount + LOAD_SIZE, sizeInRepo)

        isLoading = true
        coroutineScope.launch {
            try {
                val newProducts = productRepo.getProducts(
                    fromIndex = visibleProducts.size,
                    loadSize = LOAD_SIZE
                )
                visibleProducts = visibleProducts + newProducts

                hasNext = productRepo.hasNext(visibleProducts.lastIndex)
                sizeInRepo = productRepo.getSize()
            } finally {
                isLoading = false
            }
        }
    }

    companion object {
        fun Saver(
            productRepo: ProductRepository,
            coroutineScope: CoroutineScope
        ): Saver<ShoppingScreenState, Int> {
            return Saver(
                save = { state ->
                    state.visibleCount
                },
                restore = { savedCount ->
                    ShoppingScreenState(
                        productRepo = productRepo,
                        coroutineScope = coroutineScope,
                        initialVisibleCount = savedCount
                    )
                }
            )
        }
    }
}

@Composable
fun rememberShoppingScreenState(
    productRepo: ProductRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): ShoppingScreenState {
    val saver = ShoppingScreenState.Saver(productRepo, coroutineScope)
    return rememberSaveable(saver = saver) {
        ShoppingScreenState(
            productRepo = productRepo,
            coroutineScope = coroutineScope,
            initialVisibleCount = LOAD_SIZE,
        )
    }
}
