package woowacourse.shopping.data.cart

import androidx.annotation.VisibleForTesting
import woowacourse.shopping.data.shopping.DummyShoppingDataSource

object CartRepositoryInjector {
    @Volatile
    private var instance: CartRepository? = null

    fun cartRepository(): CartRepository =
        instance ?: synchronized(this) {
            instance ?: DefaultCartRepository(
                DummyCartDataSource,
                DummyShoppingDataSource,
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
