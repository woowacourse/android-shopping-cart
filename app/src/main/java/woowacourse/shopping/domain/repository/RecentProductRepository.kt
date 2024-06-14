package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct

interface RecentProductRepository {
    fun save(product: Product)

    fun loadLatest(): RecentProduct?

    fun loadSecondLatest(): RecentProduct?

    fun loadLatestList(): List<RecentProduct>
}
