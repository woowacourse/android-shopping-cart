package woowacourse.shopping.util

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.remote.MockShoppingWebServer
import woowacourse.shopping.data.repository.FakeCartRepository
import woowacourse.shopping.data.repository.FakeProductRepository
import woowacourse.shopping.presentation.cart.CartViewModelFactory
import woowacourse.shopping.presentation.home.HomeViewModelFactory

class FakeShoppingApplication : Application(), ShoppingApplication {
    override val productRepository by lazy { FakeProductRepository() }
    override val cartRepository by lazy { FakeCartRepository() }
    override val cartViewModelFactory: CartViewModelFactory =
        CartViewModelFactory(cartRepository, productRepository)
    override val homeViewModelFactory: HomeViewModelFactory =
        HomeViewModelFactory(productRepository, cartRepository)
}
