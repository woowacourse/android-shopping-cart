package woowacourse.shopping.data.repository.product

import woowacourse.shopping.data.network.product.ProductDao
import woowacourse.shopping.domain.Product

class ProductRepositoryImpl(
    private val productDao: ProductDao,
) : ProductRepository {
    override suspend fun loadProducts(
        startIndex: Int,
        pageSize: Int,
    ): List<Product> = productDao.findAllProduct(
        startIndex = startIndex,
        pageSize = pageSize,
    )

    override suspend fun getProduct(id: String): Product = productDao.findById(id = id)
}
