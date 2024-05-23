package woowacourse.shopping.data.cart

import android.content.Context
import androidx.annotation.VisibleForTesting
import woowacourse.shopping.data.shopping.product.ProductDataSourceInjector
import woowacourse.shopping.domain.repository.CartRepository

object CartRepositoryInjector {
    @Volatile
    private var instance: CartRepository? = null

    fun cartRepository(context: Context): CartRepository =
        instance ?: synchronized(this) {
            instance ?: DefaultCartRepository(
                CartDataSourceInjector.cartDataSource(context),
                ProductDataSourceInjector.productDataSource(),
            ).also { instance = it }
        }

    @VisibleForTesting
    fun setCartRepository(cartRepository: CartRepository) {
        instance = cartRepository
    }

    @VisibleForTesting
    fun clear() {
        instance = null
    }
}
