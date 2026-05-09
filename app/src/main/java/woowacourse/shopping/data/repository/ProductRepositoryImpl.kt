package woowacourse.shopping.data.repository

import woowacourse.shopping.data.remote.model.ProductResponse
import woowacourse.shopping.data.remote.source.ProductRemoteDataSource
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(private val remoteDataSource: ProductRemoteDataSource) : ProductRepository {
    override suspend fun getProducts(): List<Product> = remoteDataSource.getProducts().map { response ->
        response.toProduct()
    }

    override suspend fun getProduct(id: String): Product? = try {
        remoteDataSource.getProduct(id).toProduct()
    } catch (e: Exception) {
        println("ProductRepository Exception: $e.message")
        null
    }

    private fun ProductResponse.toProduct(): Product = Product(
        name = this.name,
        price = Money(this.price),
        imageUrl = this.imageUrl,
        id = this.id.toString(),
    )
}
