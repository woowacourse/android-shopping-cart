package woowacourse.shopping.data

import com.example.domain.ProductCache
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductRemoteMockRepositoryImpl(
    private val service: ProductMockService,
    private val productCache: ProductCache
) : ProductRepository {
    override fun getFirstProducts(onSuccess: (List<Product>) -> Unit) {
        if (productCache.productList.isEmpty()) {
            service.request(
                page = 1,
                onSuccess = {
                    productCache.addProducts(it)
                    onSuccess(it)
                },
                onFailure = { onSuccess(emptyList()) }
            )
        } else {
            onSuccess(productCache.productList)
        }
    }

    override fun getNextProducts(onSuccess: (List<Product>) -> Unit) {
        val currentPage = (productCache.productList.size - 1) / LOAD_SIZE + 1
        service.request(
            currentPage + 1,
            onSuccess = {
                productCache.addProducts(it)
                onSuccess(it)
            },
            onFailure = { }
        )
    }

    override fun clearCache() {
        productCache.clear()
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
