package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repsoitory.DummyProductRepositoryImpl
import woowacourse.shopping.data.repsoitory.DummyShoppingCartRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class App : Application() {
    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCartRepositoryImpl }
    val productRepository: ProductRepository by lazy { DummyProductRepositoryImpl }

    override fun onCreate() {
        super.onCreate()
    }
}
