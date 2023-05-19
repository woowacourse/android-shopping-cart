package woowacourse.shopping.data.repository.local

import com.example.domain.cache.ProductCache
import com.example.domain.cache.ProductLocalCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.ProductRepository.Companion.LOAD_SIZE

class ProductMockRepositoryImpl(
    private val dataSource: List<Product> = productsDatasource,
    override val cache: ProductCache = ProductLocalCache
) : ProductRepository {

    override fun getFirstProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        if (cache.productList.isEmpty()) {
            kotlin.runCatching { dataSource.take(LOAD_SIZE) }.onSuccess {
                cache.addProducts(it)
                onSuccess(it)
            }.onFailure {
                onFailure()
            }
        } else {
            onSuccess(cache.productList)
        }
    }

    override fun getNextProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        val cacheData = cache.productList.filter { it.id > lastProductId }
        if (cacheData.isNotEmpty()) return onSuccess(cacheData.take(LOAD_SIZE))

        kotlin.runCatching { dataSource.filter { it.id > lastProductId }.take(LOAD_SIZE) }
            .onSuccess {
                cache.addProducts(it)
                onSuccess(it)
            }.onFailure {
                onFailure()
            }
    }

    override fun resetCache() {
        cache.clear()
    }
}
