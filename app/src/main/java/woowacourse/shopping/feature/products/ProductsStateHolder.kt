package woowacourse.shopping.feature.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.feature.products.model.ShoppingProductInfo
import woowacourse.shopping.feature.products.model.toUiModel

class ProductsStateHolder(
    private val productRepository: ProductRepository,
) {
    private val totalCount = productRepository.getProductCount()
    private var pageCount = 0

    var products by mutableStateOf(emptyList<ShoppingProductInfo>().toImmutableList())
        private set

    var isLastPage by mutableStateOf(false)
        private set

    init {
        getProducts()
    }

    fun getProducts(pageSize: Int = 20) {
        val currentProducts =
            productRepository
                .getProducts(pageCount, pageSize)
                .items
                .map { it.toUiModel() }
                .toImmutableList()

        if (currentProducts.isNotEmpty()) {
            products = (products + currentProducts).toImmutableList()
            pageCount++
        }

        isLastPage = products.size >= totalCount
    }
}

@Composable
fun retainProductsStateHolder(): ProductsStateHolder =
    retain {
        ProductsStateHolder(ProductRepositoryImpl)
    }
