package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity
import woowacourse.shopping.data.local.mapper.toDomain
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository

class RecentlyViewedProductRepositoryImpl(
    private val dao: RecentlyViewedProductDao,
) : RecentlyViewedProductRepository {
    override suspend fun saveViewedProduct(product: Product) {
        dao.save(
            RecentlyViewedProductEntity(
                productId = product.productId,
                productName = product.productName,
                imageUrl = product.imageUrl,
                price = product.price.value,
                viewedAt = System.currentTimeMillis(),
            ),
        )
        dao.deleteOverLimit()
    }

    override suspend fun getRecentlyViewedProducts(): RecentlyViewedProducts {
        val products =
            dao.findAll().map { it.toDomain() }

        return RecentlyViewedProducts(products)
    }
}
