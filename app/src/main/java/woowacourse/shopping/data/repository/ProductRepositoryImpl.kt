package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.dto.response.toDomainModel
import woowacourse.shopping.domain.model.product.ProductItems
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository {
    override fun getProducts(): ProductItems {
        val productResult = dataSource.getProducts()
        val productItems = productResult.map { it.toDomainModel() }
        return ProductItems(productItems)
    }
}
