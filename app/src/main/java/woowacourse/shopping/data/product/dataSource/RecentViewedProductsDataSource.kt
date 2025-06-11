package woowacourse.shopping.data.product.dataSource

import woowacourse.shopping.data.product.entity.RecentViewedProductEntity

interface RecentViewedProductsDataSource {
    fun load(): List<RecentViewedProductEntity>

    fun upsert(product: RecentViewedProductEntity)
}
