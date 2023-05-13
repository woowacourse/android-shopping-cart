package woowacourse.shopping.data

import com.example.domain.cache.ProductCache
import com.example.domain.cache.ProductLocalCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductMockRepositoryImpl(
    private val dataSource: List<Product> = productsDatasource,
    override val cache: ProductCache = ProductLocalCache
) : ProductRepository {

    override fun getFirstProducts(): List<Product> {
        if (cache.productList.isEmpty()) {
            val products = dataSource.take(LOAD_SIZE)
            cache.addProducts(products)
            return products
        }
        return cache.productList
    }

    override fun getNextProducts(lastProductId: Long): List<Product> {
        val cacheData = cache.productList.filter { it.id > lastProductId }
        if (cacheData.isNotEmpty()) return cacheData.take(LOAD_SIZE)

        val newProducts = dataSource.filter { it.id > lastProductId }.take(LOAD_SIZE)
        cache.addProducts(newProducts)
        return newProducts
    }

    override fun resetCache() {
        cache.clear()
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
