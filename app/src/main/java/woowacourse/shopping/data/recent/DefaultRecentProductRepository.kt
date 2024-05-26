package woowacourse.shopping.data.recent

import android.content.Context
import woowacourse.shopping.data.shopping.DummyShoppingDataSource
import woowacourse.shopping.data.shopping.ShoppingDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

class DefaultRecentProductRepository(
    private val context: Context,
    private val recentProductDataSource: RecentProductDataSource = DummyRecentProductDataSource(
        context
    ),
    private val shoppingDataSource: ShoppingDataSource = DummyShoppingDataSource,
) : RecentProductRepository {
    override fun recentProducts(): List<RecentProduct> {
        return recentProductDataSource.recentProducts()
    }

    override fun addRecentProduct(productId: Long) {
        val product = shoppingDataSource.productById(productId) ?: return
        recentProductDataSource.addRecentProduct(product, System.currentTimeMillis())
    }

    override fun lastViewedProduct(): RecentProduct? {
        return recentProductDataSource.lastViewedProduct()
    }
}
