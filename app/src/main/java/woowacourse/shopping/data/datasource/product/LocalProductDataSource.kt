package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.db.dao.ProductDao
import woowacourse.shopping.data.db.mapper.toProduct
import woowacourse.shopping.domain.datasource.ProductDataSource
import woowacourse.shopping.domain.model.Product

class LocalProductDataSource(
    private val productDao: ProductDao,
) : ProductDataSource {
    override fun findProductById(id: Int): Result<Product> =
        runCatching {
            val entity = productDao.findById(id) ?: throw NoSuchElementException()
            entity.toProduct()
        }

    override fun getOffsetRanged(
        offset: Int,
        size: Int,
    ): Result<List<Product>> =
        runCatching {
            val entityList =
                productDao.findByOffsetAndSize(offset, size)
            entityList.toProduct()
        }
}
