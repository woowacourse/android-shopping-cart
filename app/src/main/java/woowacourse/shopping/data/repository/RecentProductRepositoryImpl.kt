package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.recentproduct.RecentProductDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val localRecentProductDataSource: RecentProductDataSource.Local,
) : RecentProductRepository {

    override fun add(recentProduct: RecentProduct) {
        localRecentProductDataSource.add(recentProduct.toData())
    }

    override fun getPartially(size: Int): RecentProducts =
        RecentProducts(localRecentProductDataSource.getPartially(size).toDomain())
}
