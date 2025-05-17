package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initShoppingProvider()
    }

    private fun initShoppingProvider() {
        val database = ShoppingCartDatabase.getDataBase(this)
        val shoppingCartDao = database.shoppingCartDao()
        ShoppingProvider.initProductRepository(ShoppingCartRepositoryImpl(shoppingCartDao))
    }
}
