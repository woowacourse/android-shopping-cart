package woowacourse.shopping.data.recentproduct

import java.time.LocalDateTime
import kotlin.concurrent.thread

class RecentProductRepositoryImpl private constructor(private val recentProductDao: RecentProductDao) :
    RecentProductRepository {
        override fun insert(productId: Long): Long {
            var id = -1L
            thread {
                id =
                    recentProductDao.insert(
                        RecentProduct(
                            productId = productId,
                            recentTime = LocalDateTime.now(),
                        ),
                    )
            }.join()
            return id
        }

        override fun findMostRecentProduct(): RecentProduct? {
            var recentProduct: RecentProduct? = null
            thread {
                recentProduct = recentProductDao.findMostRecentProduct()
            }.join()
            return recentProduct
        }

        override fun findAll(): List<RecentProduct> {
            var recentProducts = emptyList<RecentProduct>()
            thread {
                recentProducts = recentProductDao.findAll()
            }.join()
            return recentProducts
        }

        override fun deleteAll() {
            thread {
                recentProductDao.deleteAll()
            }.join()
        }

        companion object {
            private var instance: RecentProductRepository? = null

            fun get(recentProductDao: RecentProductDao): RecentProductRepository {
                return instance ?: synchronized(this) {
                    instance ?: RecentProductRepositoryImpl(recentProductDao).also { instance = it }
                }
            }
        }
    }
