package woowacourse.shopping.data.recent

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(
    private val dao: RecentProductDao,
) : RecentProductRepository {
    override fun insert(productId: Long) {
        thread { dao.insert(RecentProductEntity(productId = productId)) }.join()
    }

    override fun getAll(): List<RecentProduct> {
        var result = listOf<RecentProduct>()
        thread { result = dao.getAll().map { it.toDomain() } }.join()
        return result
    }

    override fun getLastProduct(): RecentProduct? {
        var result: RecentProduct? = null
        thread { result = dao.getLastProduct()?.toDomain() }.join()
        return result
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<RecentProduct> {
        var result = listOf<RecentProduct>()
        thread { result = dao.getPaged(limit, offset).map { it.toDomain() } }.join()
        return result
    }

    override fun replaceRecentProduct(productId: Long) {
        thread { dao.replaceRecentProduct(productId) }.join()
    }
}
