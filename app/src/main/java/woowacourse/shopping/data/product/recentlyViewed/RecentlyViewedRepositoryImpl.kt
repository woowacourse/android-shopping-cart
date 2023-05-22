package woowacourse.shopping.data.product.recentlyViewed

import woowacourse.shopping.data.product.ProductDataSource
import woowacourse.shopping.data.product.ProductMapper.toDomainModel
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.RecentlyViewedRepository
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult

class RecentlyViewedRepositoryImpl(
    private val recentlyViewedDataSource: RecentlyViewedDataSource,
    private val productDataSource: ProductDataSource,
) : RecentlyViewedRepository {
    override fun getRecentlyViewedProducts(unit: Int): List<RecentlyViewedProduct> {
        val recentlyViewed: List<RecentlyViewedEntity> =
            recentlyViewedDataSource.getRecentlyViewedProducts(unit)
        return recentlyViewed.mapNotNull {
            val product = productDataSource.getProductEntity(it.productId)?.toDomainModel()
                ?: return@mapNotNull null
            return@mapNotNull RecentlyViewedProduct(it.viewedDateTime, product)
        }
    }

    override fun getLastViewedProduct(): WoowaResult<RecentlyViewedProduct> {
        val entity = recentlyViewedDataSource.getLastViewedProduct()
            ?: return WoowaResult.FAIL(Error.DataBaseEmpty)
        val product = productDataSource.getProductEntity(entity.productId)?.toDomainModel()
            ?: return WoowaResult.FAIL(Error.DataBaseError)
        return WoowaResult.SUCCESS(RecentlyViewedProduct(entity.viewedDateTime, product))
    }

    override fun addRecentlyViewedProduct(productId: Long): Long {
        return recentlyViewedDataSource.addRecentlyViewedProduct(productId)
    }
}
