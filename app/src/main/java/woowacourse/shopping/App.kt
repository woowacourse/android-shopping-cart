package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.ShoppingCartDataSource
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.repository.ProductRepositoryImpl
import woowacourse.shopping.repository.ShoppingCartRepositoryImpl

class App : Application() {
    private val database by lazy { ShoppingCartDatabase.getDataBase(this) }
    private val cartDao by lazy { database.cartDao() }
    private val shoppingCartDataSource by lazy { ShoppingCartDataSource(cartDao) }
    val shoppingCartRepository by lazy { ShoppingCartRepositoryImpl(shoppingCartDataSource) }
    val productRepository by lazy { ProductRepositoryImpl(DummyProducts) }
}
