package woowacourse.shopping.data.repository

import kotlinx.coroutines.CancellationException
import woowacourse.shopping.data.remote.model.ProductResponse
import woowacourse.shopping.data.remote.source.ProductRemoteDataSource
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(private val remoteDataSource: ProductRemoteDataSource) : ProductRepository {
    override suspend fun getProducts(): List<Product> = remoteDataSource.getProducts().map { response ->
        response.toProduct()
    }

    override suspend fun getProduct(id: String): Result<Product?> = try {
        val productResponse = remoteDataSource.getProduct(id)
        Result.success(productResponse.toProduct())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun ProductResponse.toProduct(): Product = Product(
        name = this.name,
        price = Money(this.price),
        imageUrl = this.imageUrl,
        id = this.id.toString(),
    )
}
