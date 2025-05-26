package woowacourse.shopping.data.product.dataSource

import woowacourse.shopping.data.product.dao.RecentViewedProductDao
import woowacourse.shopping.data.product.entity.ProductEntity

object LocalRecentViewedProductsDataSource : RecentViewedProductsDataSource {
    private lateinit var dao: RecentViewedProductDao

    override fun load(): List<ProductEntity> =
        listOf(
            ProductEntity(id = 1, name = "럭키", price = 4000),
            ProductEntity(id = 2, name = "아이다", price = 700),
            ProductEntity(id = 3, name = "설백", price = 1_000),
            ProductEntity(id = 4, name = "줌마", price = 1_000),
        )
}
