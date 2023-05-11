package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.DomainProductRepository

class ProductRepository(
    private val localProductDataSource: BasketDataSource.Local
) : DomainProductRepository {
    override fun getAll(): List<Product> =
        localProductDataSource.getAll().map { it.toDomain() }
}
