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

    suspend fun getProducts() {
        products += productRepository.getProducts()
        hasNext = productRepository.hasNext
    }
}
