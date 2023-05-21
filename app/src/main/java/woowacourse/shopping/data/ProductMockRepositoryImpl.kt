package woowacourse.shopping.data

import com.example.domain.ProductCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductMockRepositoryImpl(
    override val productCache: ProductCache = ProductCacheImpl
) : ProductRepository {

    override fun getFirstProducts(onSuccess: (List<Product>) -> Unit) {
        if (productCache.productList.isEmpty()) {
            val products = getProducts()
            onSuccess(products)
        }
        onSuccess(productCache.productList)
    }

    private fun getProducts() = if (LOAD_SIZE > productsDatasource.size) {
        productsDatasource
    } else {
        productsDatasource.subList(0, LOAD_SIZE)
    }

    override fun getNextProducts(onSuccess: (List<Product>) -> Unit) {
        val startIndex = productCache.productList.size
        val toIndex = (startIndex + LOAD_SIZE)
        val products = if (toIndex > productsDatasource.size) {
            productsDatasource.subList((startIndex), productsDatasource.size)
        } else {
            productsDatasource.subList((startIndex), toIndex)
        }
        onSuccess(products)
    }

    override fun clearCache() {
        productCache.clear()
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
