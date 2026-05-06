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
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository

private const val PAGE_SIZE = 20

class ShoppingScreenState(
    private val productRepo: ProductRepository,
    private val coroutineScope: CoroutineScope,
    initialVisibleCount: Int,
) {
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var visibleCount: Int by mutableIntStateOf(initialVisibleCount)
        private set
    var visibleProducts: Products by mutableStateOf(Products(emptyList()))
        private set
    var hasNext: Boolean by mutableStateOf(false)
        private set
    var sizeInRepo: Int by mutableIntStateOf(0)
        private set

    init {
        if (visibleProducts.toList().isEmpty()) {
            loadProducts()
        }
    }

    fun loadMore() {
        visibleCount = minOf(visibleCount + PAGE_SIZE, sizeInRepo)
        loadProducts()
    }

    private fun loadProducts() {
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
            initialVisibleCount = PAGE_SIZE,
        )
    }
}
