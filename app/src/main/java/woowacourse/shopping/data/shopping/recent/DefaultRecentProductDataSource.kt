package woowacourse.shopping.data.shopping.recent

import woowacourse.shopping.local.dao.RecentProductDao
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

class DefaultRecentProductDataSource(
    private val executors: ExecutorService,
    private val recentProductDao: RecentProductDao,
) : RecentProductDataSource {
    override fun recentProducts(size: Int): Result<List<RecentProductData>> {
        return runCatching {
            executors.submit(
                Callable {
                    recentProductDao.loadProducts(size)
                },
            )[TIME_OUT, TimeUnit.SECONDS].map { it.toData() }
        }
    }

    override fun saveRecentProduct(product: RecentProductData): Result<Long> {
        return runCatching {
            executors.submit(
                Callable {
                    recentProductDao.saveProduct(product.toEntity())
                },
            )[TIME_OUT, TimeUnit.SECONDS]
        }
    }

    companion object {
        private const val TIME_OUT = 3L
    }
}
