package woowacourse.shopping.local.datasource

import woowacourse.shopping.data.datasource.local.ProductHistoryDataSource
import woowacourse.shopping.data.model.local.ProductHistoryDto
import woowacourse.shopping.local.dao.ProductHistoryDao
import woowacourse.shopping.local.mapper.toData
import woowacourse.shopping.local.model.ProductHistoryEntity
import java.time.LocalDateTime

class ProductHistoryDataSourceImpl(private val dao: ProductHistoryDao) : ProductHistoryDataSource {
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

    override fun findProductHistory(productId: Long): Result<ProductHistoryDto> =
        runCatching {
            dao.findProductHistory(productId = productId).toData()
        }

    override fun getProductHistory(size: Int): Result<List<ProductHistoryDto>> =
        runCatching {
            dao.getProductHistoryPaged(size = size).map { it.toData() }
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
