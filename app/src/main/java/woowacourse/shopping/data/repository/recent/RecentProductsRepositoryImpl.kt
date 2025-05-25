package woowacourse.shopping.data.repository.recent

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
                        id = it.product?.id ?: 0,
                        name = it.product?.name ?: "a",
                        price = it.product?.price ?: 0,
                        imageUrl = it.product?.imageUrl ?: "d",
                    ),
                viewTime = it.recentProduct.viewTime,
            )
        }
    }
}
