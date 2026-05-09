package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.dao.ProductDao
import woowacourse.shopping.repository.entity.toModel

class DatabaseProductRepository(
    val productDao: ProductDao,
) : ProductRepository {
    override suspend fun totalSize(): Int = productDao.getTotalSize()

    override suspend fun getProduct(productId: String): Product? = productDao.getProductEntity(productId.toInt())?.toModel()

    override suspend fun getProducts(
        offset: Int,
        size: Int,
    ): List<Product> = productDao.getProducts(offset, size).map { it.toModel() }
}
