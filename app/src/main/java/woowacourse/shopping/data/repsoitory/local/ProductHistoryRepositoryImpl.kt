package woowacourse.shopping.data.repsoitory.local

import woowacourse.shopping.data.dao.ProductHistoryDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.model.local.ProductHistoryEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.local.ProductHistoryRepository
import java.time.LocalDateTime

class ProductHistoryRepositoryImpl(private val dao: ProductHistoryDao) : ProductHistoryRepository {
    override fun insertProductHistory(
        productId: Long,
        name: String,
        price: Int,
        imageUrl: String,
    ): Result<Unit> =
        runCatching {
            val productHistoryEntity =
                ProductHistoryEntity(
                    productId = productId,
                    name = name,
                    price = price,
                    imageUrl = imageUrl,
                    createAt = LocalDateTime.now(),
                )

            dao.insertProductHistory(productHistoryEntity = productHistoryEntity)
        }

    override fun findProductHistory(productId: Long): Result<Product> =
        runCatching {
            dao.findProductHistory(productId = productId).toDomain()
        }

    override fun getProductHistory(size: Int): Result<List<Product>> =
        runCatching {
            dao.getProductHistoryPaged(size = size).map { it.toDomain() }
        }

    override fun deleteProductHistory(productId: Long): Result<Unit> =
        runCatching {
            dao.deleteProductHistory(productId = -productId)
        }

    override fun deleteAllProductHistory(): Result<Unit> =
        runCatching {
            dao.deleteAllProductHistory()
        }
}
