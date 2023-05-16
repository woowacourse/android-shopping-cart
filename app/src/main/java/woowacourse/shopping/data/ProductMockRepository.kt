package woowacourse.shopping.data

import com.example.domain.ProductCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductMockRepository(
    private val productCache: ProductCache = ProductCacheImpl
) : ProductRepository {

    override fun getFirstProducts(): List<Product> {
        if (productCache.productList.isEmpty()) {
            val products = if (LOAD_SIZE > productsDatasource.size) {
                productsDatasource
            } else {
                productsDatasource.subList(0, LOAD_SIZE)
            }
            productCache.addProducts(products)
            return products
        }
        return productCache.productList
    }

    override fun getNextProducts(): List<Product> {
        val startIndex = productCache.productList.size
        val toIndex = (startIndex + LOAD_SIZE)
        val products = if (toIndex > productsDatasource.size) {
            productsDatasource.subList((startIndex), productsDatasource.size)
        } else {
            productsDatasource.subList((startIndex), toIndex)
        }
        productCache.addProducts(products)
        return products
    }

    override fun clearCache() {
        productCache.clear()
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
