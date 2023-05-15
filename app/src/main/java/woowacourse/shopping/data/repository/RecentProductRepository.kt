package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.recentproduct.RecentProductDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.DomainRecentProductRepository

class RecentProductRepository(private val localRecentProductDataSource: RecentProductDataSource.Local) :
    DomainRecentProductRepository {
    override fun add(recentProduct: Product) {
        localRecentProductDataSource.add(recentProduct.toData())
    }

    override fun getPartially(size: Int): List<RecentProduct> =
        localRecentProductDataSource.getPartially(size).map { it.toDomain() }
}
