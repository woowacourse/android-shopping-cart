package woowacourse.shopping.data

import com.example.domain.ProductCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductMockRepositoryImpl(
    private val productCache: ProductCache = ProductCacheImpl
) : ProductRepository {
    override fun getProducts(page: Int, onSuccess: (List<Product>) -> Unit) {
        if (productCache.productList.size >= page * LOAD_SIZE) {
            onSuccess(productCache.getSubProducts(page, LOAD_SIZE))
        } else {
            val startIndex = page * LOAD_SIZE
            val toIndex = (startIndex + LOAD_SIZE)
            val products = if (toIndex > productsDatasource.size) {
                productsDatasource.subList((startIndex), productsDatasource.size)
            } else {
                productsDatasource.subList((startIndex), toIndex)
            }
            onSuccess(products)
        }
    }

    override fun clearCache() {
        productCache.clear()
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
