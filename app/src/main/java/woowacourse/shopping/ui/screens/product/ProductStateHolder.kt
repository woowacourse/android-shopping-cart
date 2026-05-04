package woowacourse.shopping.ui.screens.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductStateHolder(
    private val productRepository: ProductRepository,
    private val initialSize: Int = 0,
) {
    var products: List<Product> by mutableStateOf(emptyList())
        private set

    var hasNext by mutableStateOf(false)
        private set

    private var isProductLoading = false

    suspend fun getProducts() {
        if (isProductLoading) return

        isProductLoading = true

        try {
            products += productRepository.getProducts(products.size, PAGE_SIZE)
            hasNext = productRepository.productSize > products.size
        } finally {
            isProductLoading = false
        }
    }

    suspend fun initialProducts() {
        val count = maxOf(initialSize, PAGE_SIZE)

        products = productRepository.getProducts(0, count)
        hasNext = productRepository.productSize > products.size
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
