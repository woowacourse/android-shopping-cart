package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.local.ProductHistoryDataSource
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductHistoryRepository

class ProductHistoryRepositoryImpl(private val dataSource: ProductHistoryDataSource) :
    ProductHistoryRepository {
    override fun insertProductHistory(
        productId: Long,
        name: String,
        price: Int,
        imageUrl: String,
    ): Result<Unit> =
        dataSource.insertProductHistory(
            productId = productId,
            name = name,
            price = price,
            imageUrl = imageUrl,
        )

    override fun findProductHistory(productId: Long): Result<Product> =
        dataSource.findProductHistory(productId = productId).mapCatching { it.toDomain() }

    override fun getProductHistory(size: Int): Result<List<Product>> =
        dataSource.getProductHistory(size = size)
            .mapCatching { result -> result.map { it.toDomain() } }

    override fun deleteProductHistory(productId: Long): Result<Unit> = dataSource.deleteProductHistory(productId = productId)

    override fun deleteAllProductHistory(): Result<Unit> = dataSource.deleteAllProductHistory()
}
