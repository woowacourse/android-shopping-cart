package woowacourse.shopping.ui.shopping

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository

class ShoppingScreenState(
    private val productRepo: ProductRepository,
    private val coroutineScope: CoroutineScope,
    visibleCount: Int,
) {
    var isLoading: Boolean by mutableStateOf(false)
    var visibleProducts: Products by mutableStateOf(Products(emptyList()))
        private set
    var hasNext: Boolean by mutableStateOf(false)
        private set
    var sizeInRepo: Int by mutableIntStateOf(0)
        private set

    init {
        if (visibleProducts.toList().isEmpty()) {
            loadProducts(visibleCount)
        }
    }

    fun loadProducts(newCount: Int) {
        isLoading = true
        coroutineScope.launch {
            try {
                visibleProducts = productRepo.getProducts(0, newCount)
                hasNext = productRepo.hasNext(visibleProducts.count() - 1)
                sizeInRepo = productRepo.getSize()
            } finally {
                isLoading = false
            }
        }
    }
}

@Composable
fun rememberShoppingScreenState(
    productRepo: ProductRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    visibleCount: Int,
): ShoppingScreenState {
    return remember {
        ShoppingScreenState(
            productRepo = productRepo,
            coroutineScope = coroutineScope,
            visibleCount = visibleCount,
        )
    }
}
