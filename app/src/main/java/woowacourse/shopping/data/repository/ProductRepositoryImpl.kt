package woowacourse.shopping.data.repository

import woowacourse.shopping.data.remote.ProductRemoteDataSource
import woowacourse.shopping.data.util.toDomain
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val dataSource: ProductRemoteDataSource,
) : ProductRepository {
    override suspend fun getProductById(id: String): Product {
        val response = dataSource.getProductById(id)
        return response.toDomain()
    }

    override suspend fun getProductsByIds(ids: List<String>): List<Product> {
        val response = dataSource.getProductsByIds(ids)
        return response.map { it.toDomain() }
    }

    override suspend fun getProducts(offset: Int): Products {
        val response = dataSource.getProducts(offset / PAGE_SIZE, PAGE_SIZE)
        return response.toDomain()
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
