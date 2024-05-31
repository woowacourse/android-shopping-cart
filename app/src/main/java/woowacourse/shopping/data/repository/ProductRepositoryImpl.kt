package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasourceimpl.ProductLocalDataSource
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ProductRepositoryImpl(
    private val productLocalDataSource: ProductLocalDataSource,
) : ShoppingItemsRepository {
    override fun insertProducts(products: List<ProductEntity>) {
        productLocalDataSource.insertProducts(products)
    }

    override fun productWithQuantityItem(productId: Long): Result<ProductWithQuantity> {
        return productLocalDataSource.productWithQuantityItem(productId)
    }

    override fun findProductWithQuantityItemsByPage(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductWithQuantity>> {
        return productLocalDataSource.findProductWithQuantityItemsByPage(page, pageSize)
    }
}
