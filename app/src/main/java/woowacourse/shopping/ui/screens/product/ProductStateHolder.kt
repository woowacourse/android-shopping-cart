package woowacourse.shopping.ui.screens.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductStateHolder(
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
) {
    var products: List<Product> by mutableStateOf(emptyList())
        private set

    var hasNext by mutableStateOf(false)
        private set

    private var isProductLoading = false

    suspend fun getProducts() {
        if (isProductLoading) return

        isProductLoading = true

        products += productRepository.getProducts(products.size, PAGE_SIZE)
        hasNext = productRepository.productSize > products.size

        isProductLoading = false
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
