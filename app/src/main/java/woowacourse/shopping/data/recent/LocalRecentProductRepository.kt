package woowacourse.shopping.data.recent

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.RecentProduct
import kotlin.concurrent.thread

class LocalRecentProductRepository(
    private val dao: RecentProductDao,
) : RecentProductRepository {
    override fun insert(productId: Long) {
        thread { dao.insert(RecentProductEntity(productId = productId)) }.join()
    }

    override fun getAll(): List<RecentProduct> {
        var result = listOf<RecentProduct>()
        thread { result = dao.getAll().toDomain() }.join()
        return result
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<RecentProduct> {
        var result = listOf<RecentProduct>()
        thread { result = dao.getPaged(limit, offset).toDomain() }.join()
        return result
    }

    override fun replaceRecentProduct(productId: Long) {
        thread { dao.replaceRecentProduct(productId) }.join()
    }
}
