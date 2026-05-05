package woowacourse.shopping.features.productList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.product.ProductRepositoryMockImpl
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductListStateHolder(
    private val productRepository: ProductRepository,
) {
    var products by mutableStateOf(emptyList<Product>())
    var pageCount by mutableStateOf(0)
    var totalProductCount = productRepository.getProductsSize()
    var isLastPage by mutableStateOf(false)

    init {
        loadProducts()
    }

    fun loadProducts() {
        val moreProducts =
            productRepository.getPagedProducts(page = pageCount, pageSize = PAGE_SIZE)
        if (moreProducts.isEmpty()) return
        products = products + moreProducts
        pageCount += 1
        isLastPage = products.size >= totalProductCount
    }

    fun isHasProductId(productId: String): Boolean = productRepository.isProductExist(productId)

    companion object {
        const val PAGE_SIZE = 20
    }
}

@Composable
fun retainProductListStateHolder(): ProductListStateHolder =
    retain {
        ProductListStateHolder(productRepository = ProductRepositoryMockImpl())
    }
