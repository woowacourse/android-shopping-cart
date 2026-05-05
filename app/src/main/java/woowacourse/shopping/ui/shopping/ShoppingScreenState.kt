package woowacourse.shopping.ui.shopping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository

class ShoppingScreenState(
    private val productRepo: ProductRepository,
    private val coroutineScope: CoroutineScope,
    visibleCount: Int
) {
    var visibleProducts: Products by mutableStateOf(Products(emptyList()))
        private set
    var hasNext: Boolean by mutableStateOf(true)
        private set
    var sizeInRepo: Int by mutableIntStateOf(0)
        private set

    init {
        loadProducts(visibleCount)
    }

    fun loadProducts(newCount: Int) {
        coroutineScope.launch {
            visibleProducts = productRepo.getProducts(0, newCount)
            hasNext = productRepo.hasNext(visibleProducts.count() - 1)
            sizeInRepo = productRepo.getSize()
        }
    }
}
