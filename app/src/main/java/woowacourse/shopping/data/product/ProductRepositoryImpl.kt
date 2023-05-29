package woowacourse.shopping.data.product

import android.util.Log
import woowacourse.shopping.data.product.ProductMapper.toDomainModel
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDataSource
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val recentlyViewedDataSource: RecentlyViewedDataSource,
) : ProductRepository {

    override fun getProduct(id: Long): WoowaResult<Product> {
        val productEntity: ProductEntity = getProductEntity(id) ?: return FAIL(Error.NoSuchId)

        return SUCCESS(productEntity.toDomainModel())
    }

    override fun getProducts(unit: Int, lastIndex: Int): List<Product> {
        return productDataSource.getProductEntities(unit, lastIndex).map { productEntity ->
            productEntity.toDomainModel()
        }
    }

    override fun getRecentlyViewedProducts(unit: Int): List<Product> {
        val recentlyViewed: List<RecentlyViewedEntity> =
            recentlyViewedDataSource.getRecentlyViewedProducts(unit)
        val productEntities: List<ProductEntity> =
            recentlyViewed.mapNotNull { getProductEntity(it.productId) }
        return productEntities.map { it.toDomainModel() }
    }

    override fun getLastViewedProduct(): WoowaResult<Product> {
        val lastViewed: List<RecentlyViewedEntity> =
            recentlyViewedDataSource.getLastViewedProduct()

        Log.d("123123", lastViewed.toString())
        return when {
            lastViewed.isEmpty() -> FAIL(Error.NoSuchId)
            lastViewed.size > 1 -> getProductEntity(lastViewed.first().productId)?.let {
                SUCCESS(it.toDomainModel())
            } ?: FAIL(Error.NoSuchId)

            lastViewed.size == 1 -> getProductEntity(lastViewed.last().productId)?.let {
                SUCCESS(it.toDomainModel())
            } ?: FAIL(Error.NoSuchId)

            else -> throw IllegalArgumentException()
        }
    }

    private fun getProductEntity(id: Long): ProductEntity? {
        return productDataSource.getProductEntity(id)
    }

    override fun addRecentlyViewedProduct(productId: Long, unit: Int): Long {
        recentlyViewedDataSource.deleteRecentlyViewedProduct(productId)
        return recentlyViewedDataSource.addRecentlyViewedProduct(productId, unit)
    }
}
