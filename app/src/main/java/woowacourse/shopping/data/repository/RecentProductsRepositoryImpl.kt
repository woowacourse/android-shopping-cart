package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

class RecentProductsRepositoryImpl(
    private val recentProductDao: RecentProductDao,
) : RecentProductsRepository {
    override fun update(product: RecentProduct) {
        recentProductDao.save(
            RecentProductEntity(
                productId = product.product.id,
                viewTime = product.viewTime,
            ),
        )
    }

    override fun insert(product: RecentProduct) {
        recentProductDao.insert(
            RecentProductEntity(
                productId = product.product.id,
                viewTime = product.viewTime,
            ),
        )
    }

    override fun findAll(): List<RecentProduct> {
        return recentProductDao.findAll().map {
            RecentProduct(
                product =
                    Product(
                        id = it.product.id,
                        name = it.product.name,
                        price = it.product.price,
                        imageUrl = it.product.imageUrl,
                    ),
                viewTime = it.recentProduct.viewTime,
            )
        }
    }
}
