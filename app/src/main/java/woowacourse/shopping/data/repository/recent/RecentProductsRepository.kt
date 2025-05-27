package woowacourse.shopping.data.repository.recent

import woowacourse.shopping.domain.RecentProduct

interface RecentProductsRepository {
    fun update(product: RecentProduct)

    fun insert(product: RecentProduct)

    fun findAll(): List<RecentProduct>
}
