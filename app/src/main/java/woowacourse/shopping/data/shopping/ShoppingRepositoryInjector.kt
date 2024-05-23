package woowacourse.shopping.data.shopping

import android.content.Context
import androidx.annotation.VisibleForTesting
import woowacourse.shopping.data.cart.CartDataSourceInjector
import woowacourse.shopping.data.shopping.product.ProductDataSourceInjector
import woowacourse.shopping.data.shopping.recent.RecentProductDataSourceInjector
import woowacourse.shopping.domain.repository.ShoppingRepository

object ShoppingRepositoryInjector {
    @Volatile
    private var instance: ShoppingRepository? = null

    fun shoppingRepository(context: Context): ShoppingRepository =
        instance ?: synchronized(this) {
            instance ?: DefaultShoppingRepository(
                ProductDataSourceInjector.productDataSource(),
                CartDataSourceInjector.cartDataSource(context),
                RecentProductDataSourceInjector.recentProductDataSource(context)
            ).also { instance = it }
        }

    @VisibleForTesting
    fun setShoppingRepository(shoppingRepository: ShoppingRepository) {
        instance = shoppingRepository
    }

    @VisibleForTesting
    fun clear() {
        instance = null
    }
}
