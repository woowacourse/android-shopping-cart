package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentlyProductDao
import woowacourse.shopping.data.db.RecentlyViewedProduct

class RecentProductDataSourceImpl(
    private val dao: RecentlyProductDao,
) : RecentProductDataSource {
    override fun getProducts(): List<RecentlyViewedProduct> = dao.getProducts()

    override fun insert(product: RecentlyViewedProduct) {
        val count = dao.getCount()

        if (count == 10) {
            val oldest = dao.getOldestProduct()
            if (oldest != null && oldest.productId != product.productId) {
                dao.delete(oldest)
            }
        }
        dao.insertProduct(product)
    }
}
