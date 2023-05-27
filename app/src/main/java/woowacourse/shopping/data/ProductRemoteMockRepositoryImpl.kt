package woowacourse.shopping.data

import com.example.domain.ProductCache
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductRemoteMockRepositoryImpl(
    private val service: ProductMockService,
    private val productCache: ProductCache
) : ProductRepository {
    override fun getProducts(page: Int, onSuccess: (List<Product>) -> Unit) {
        if (productCache.productList.size >= page * LOAD_SIZE) {
            onSuccess(productCache.getSubProducts(page, LOAD_SIZE))
        } else {
            service.request(
                page = page,
                onSuccess = {
                    productCache.addProducts(it)
                    onSuccess(productCache.getSubProducts(page, LOAD_SIZE))
                },
                onFailure = {}
            )
        }
    }

    override fun clearCache() {
        productCache.clear()
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
