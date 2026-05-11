package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.remote.model.ProductResponse
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override suspend fun getProductSize(): Int = productDataSource.getTotalCount()

    override suspend fun getProductById(id: String): Product = productDataSource.getProductById(id).toDomain()

    override suspend fun getProducts(
        startIndex: Int,
        count: Int,
    ): List<Product> =
        productDataSource.getProducts(startIndex, count).map {
            it.toDomain()
        }

    private fun ProductResponse.toDomain(): Product =
        Product(
            id = id.toString(),
            name = name,
            price = Price(price),
            imageUrl = imageUrl,
        )
}
