package woowacourse.shopping.data.product.dataSource

import woowacourse.shopping.data.product.entity.ProductEntity

interface RecentViewedProductsDataSource {
    fun load(): List<ProductEntity>
}
