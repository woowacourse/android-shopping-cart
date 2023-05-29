package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.product.ProductDataSource
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val localProductDataSource: ProductDataSource.Local,
    private val remoteProductDataSource: ProductDataSource.Remote
) : ProductRepository {
    override fun getPartially(size: Int, lastId: Int): List<Product> =
        remoteProductDataSource.getPartially(size, lastId).map { it.toDomain() }
}
