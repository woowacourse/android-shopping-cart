package woowacourse.shopping.presentation

import android.app.Application
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryInjector
import woowacourse.shopping.data.shopping.ShoppingRepository
import woowacourse.shopping.data.shopping.ShoppingRepositoryInjector

class ShoppingApplication : Application() {
    val shoppingRepository: ShoppingRepository by lazy {
        ShoppingRepositoryInjector.shoppingRepository()
    }

    val cartRepository: CartRepository by lazy {
        CartRepositoryInjector.cartRepository()
    }
}
