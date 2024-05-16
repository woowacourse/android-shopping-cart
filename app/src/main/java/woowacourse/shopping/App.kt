package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.data.repsoitory.DummyShoppingCart
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class App : Application() {
    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCart }
    val productListRepository: ProductListRepository by lazy { DummyProductList }

    override fun onCreate() {
        super.onCreate()
    }
}
