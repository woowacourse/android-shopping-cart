package woowacourse.shopping.data

import com.example.domain.ProductCache
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductRemoteMockRepositoryImpl(
    private val webServer: ProductMockWebServer,
    override val productCache: ProductCache
) : ProductRepository {
    override fun getFirstProducts(onSuccess: (List<Product>) -> Unit) {
        if (productCache.productList.isEmpty()) {
            webServer.request(
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
        webServer.request(
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
