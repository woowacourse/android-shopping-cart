package woowacourse.shopping.data.recent

import android.content.Context
import woowacourse.shopping.data.shopping.DummyShoppingDataSource
import woowacourse.shopping.data.shopping.ShoppingDataSource
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository

class DefaultRecentProductRepository(
    private val context: Context,
    private val recentProductDataSource: RecentProductDataSource =
        DefaultRecentProductDataSource(
            context,
        ),
    private val shoppingDataSource: ShoppingDataSource = DummyShoppingDataSource,
) : RecentProductRepository {
    override fun recentProducts(size: Int): List<RecentProduct> {
        return recentProductDataSource.recentProducts(size)
    }

    override fun addRecentProduct(productId: Long) {
        val product = shoppingDataSource.productById(productId) ?: return
        recentProductDataSource.addRecentProduct(product, System.currentTimeMillis())
    }

    override fun lastViewedProduct(): RecentProduct? {
        return recentProductDataSource.lastViewedProduct()
    }
}
