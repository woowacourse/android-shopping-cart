package woowacourse.shopping.data.product

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductDataSource
import woowacourse.shopping.utils.toProduct
import woowacourse.shopping.utils.toProductEntity

class ProductDataSourceImpl(
    private val dao: ProductDao,
) : ProductDataSource {
    override fun findById(productId: Long): Result<Product?> {
        return runCatching {
            dao.findById(productId)
        }.mapCatching { productEntity ->
            productEntity?.toProduct()
        }
    }

    override fun findInRange(
        limit: Int,
        offset: Int,
    ): Result<List<Product>> {
        return runCatching {
            dao.findInRange(limit, offset)
        }.mapCatching { productEntities ->
            productEntities.map { productEntity -> productEntity.toProduct() }
        }
    }

    override fun insertAll(vararg products: Product): Result<Unit> {
        return runCatching {
            val productEntities =
                products.map { product -> product.toProductEntity() }.toTypedArray()
            dao.insertAll(*productEntities)
        }
    }
}
