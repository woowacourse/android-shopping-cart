package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dataSource.local.product.ProductDataSource
import woowacourse.shopping.data.dataSource.local.recentlyViewed.RecentlyViewedDataSource
import woowacourse.shopping.data.dataSource.remote.ProductRemoteDataSource
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedEntity
import woowacourse.shopping.data.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val recentlyViewedDataSource: RecentlyViewedDataSource,
) : ProductRepository {

    override fun getProduct(id: Long): WoowaResult<Product> {
        val productEntity: ProductEntity = getProductEntity(id) ?: return FAIL(Error.NoSuchId)

        return SUCCESS(productEntity.toDomainModel())
    }

    override fun getProductsFromLocal(unit: Int, lastIndex: Int): List<Product> {
        return productDataSource.getProductEntities(unit, lastIndex).map { productEntity ->
            productEntity.toDomainModel()
        }
    }

    override fun getProductsFromRemote(unit: Int, lastIndex: Int): WoowaResult<List<Product>> {
        return when (val result = productRemoteDataSource.getAllProducts(unit, lastIndex)) {
            is SUCCESS -> SUCCESS(result.data.map { it.toDomainModel() })
            is FAIL -> FAIL(Error.Disconnect)
        }
    }

//    private fun getProductEntity2(id: Long): ProductEntity? {
//        productRemoteDataSource.getProduct(id).enqueue()
//
//        return productDataSource.getProductEntity(id)
//    }

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
