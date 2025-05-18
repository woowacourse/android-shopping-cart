package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyProductsRepository
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.data.ProductsRepository
import woowacourse.shopping.data.ShoppingCartRepository

class ShoppingApplication : Application() {
    val productsRepository: ProductsRepository by lazy { DummyProductsRepository() }
    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCartRepository() }
}
