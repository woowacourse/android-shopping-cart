package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.cart.LocalCartProductRepository
import woowacourse.shopping.data.product.LocalProductRepository

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingCartDatabase.getDataBase(this) }
    val productRepository by lazy { LocalProductRepository() }
    val cartProductRepository by lazy { LocalCartProductRepository(database.cartProductDao) }
}
