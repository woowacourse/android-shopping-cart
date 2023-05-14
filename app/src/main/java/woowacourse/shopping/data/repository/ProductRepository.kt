package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.product.ProductDataSource
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.DomainProductRepository

class ProductRepository(
    private val localProductDataSource: ProductDataSource.Local,
) : DomainProductRepository {
    override fun getPartially(size: Int, lastId: Int): List<Product> =
        localProductDataSource.getPartially(size, lastId).map { it.toDomain() }
}
