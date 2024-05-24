package woowacourse.shopping.data.recentproduct

import java.time.LocalDateTime

class RecentProductRepositoryImpl private constructor(private val recentProductDao: RecentProductDao) :
    RecentProductRepository {
        override fun insert(productId: Long): Long =
            recentProductDao.insert(
                RecentProduct(
                    productId = productId,
                    recentTime = LocalDateTime.now(),
                ),
            )

        override fun findMostRecentProduct(): RecentProduct? = recentProductDao.findMostRecentProduct()

        override fun findAll(): List<RecentProduct> = recentProductDao.findAll()

        override fun deleteAll() = recentProductDao.deleteAll()

        companion object {
            private var instance: RecentProductRepository? = null

            fun get(recentProductDao: RecentProductDao): RecentProductRepository {
                return instance ?: synchronized(this) {
                    instance ?: RecentProductRepositoryImpl(recentProductDao).also { instance = it }
                }
            }
        }
    }
