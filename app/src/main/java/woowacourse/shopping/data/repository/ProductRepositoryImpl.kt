package woowacourse.shopping.data.repository

import woowacourse.shopping.data.remote.model.ProductResponse
import woowacourse.shopping.data.remote.source.ProductRemoteDataSource
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(private val remoteDataSource: ProductRemoteDataSource) : ProductRepository {
    override suspend fun getProducts(
        page: Int,
        size: Int,
    ): Result<List<Product>> = remoteDataSource.getProducts(page = page, size = size).map { response ->
        response.map { it.toProduct() }
    }

    override suspend fun getProduct(id: String): Result<Product?> = remoteDataSource.getProduct(id).map { it.toProduct() }

    private fun ProductResponse.toProduct(): Product = Product(
        name = this.name,
        price = Money(this.price),
        imageUrl = this.imageUrl,
        id = this.id.toString(),
    )
}
