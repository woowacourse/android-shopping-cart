package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.product.ProductDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val remoteProductDataSource: ProductDataSource.Remote,
) : ProductRepository {
    override fun getProductByPage(page: Page): List<Product> =
        remoteProductDataSource.getProductByPage(page.toData()).map { it.toDomain() }

    override fun findProductById(id: Int): Product? =
        remoteProductDataSource.findProductById(id)?.toDomain()
}
