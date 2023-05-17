package woowacourse.shopping.data.product

import woowacourse.shopping.data.product.ProductMapper.toDomainModel
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDataSource
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val recentlyViewedDataSource: RecentlyViewedDataSource,
) : ProductRepository {

    override fun getProduct(id: Long): WoowaResult<Product> {
        val productEntity: ProductEntity =
            productDataSource.getProductEntity(id) ?: return WoowaResult.FAIL(Error.NoSuchId)

        return WoowaResult.SUCCESS(productEntity.toDomainModel())
    }

    override fun getProducts(unit: Int, lastId: Long): List<Product> {
        return productDataSource.getProductEntities(unit, lastId).map { productEntity ->
            productEntity.toDomainModel()
        }
    }

    override fun getRecentlyViewedProducts(unit: Int): List<Product> {
        val recentlyViewed: List<RecentlyViewedEntity> =
            recentlyViewedDataSource.getRecentlyViewedProducts(unit)
        val productEntities: List<ProductEntity> =
            recentlyViewed.mapNotNull { productDataSource.getProductEntity(it.productId) }
        return productEntities.map { it.toDomainModel() }
    }

    override fun addRecentlyViewedProduct(productId: Long): Long {
        return recentlyViewedDataSource.addRecentlyViewedProduct(productId)
    }

    override fun isLastProduct(id: Long): Boolean {
        return productDataSource.isLastProductEntity(id)
    }
}
