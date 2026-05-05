package woowacourse.shopping.ui.screens.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductStateHolder(
    private val initialLoadedPageCount: Int = 1,
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
) {
    var products: List<Product> by mutableStateOf(emptyList())
        private set

    var hasNext by mutableStateOf(false)
        private set

    var loadedPageCount by mutableIntStateOf(initialLoadedPageCount)
        private set

    var isLoading by mutableStateOf(false)
        private set

    suspend fun initProducts() {
        if (isLoading) return
        isLoading = true
        repeat(initialLoadedPageCount) {
            products += productRepository.getProducts()
            hasNext = productRepository.hasNext
        }
        isLoading = false
    }

    suspend fun getProducts() {
        if (isLoading) return

        isLoading = true
        products += productRepository.getProducts()
        hasNext = productRepository.hasNext
        loadedPageCount++

        isLoading = false
    }

    companion object {
        val Saver: Saver<ProductStateHolder, Int> = Saver(
            save = { it.loadedPageCount },
            restore = { ProductStateHolder(initialLoadedPageCount = it) },
        )
    }
}
