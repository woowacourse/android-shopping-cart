package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initShoppingProvider()
    }

    private fun initShoppingProvider() {
        initShoppingCartRepository()
        initProductRepository()
    }

    private fun initShoppingCartRepository() {
        val database = ShoppingCartDatabase.getDataBase(this)
        val shoppingCartDao = database.shoppingCartDao()
        ShoppingProvider.initShoppingCartRepository(ShoppingCartRepositoryImpl(shoppingCartDao))
    }

    private fun initProductRepository() {
        ShoppingProvider.initProductRepository(ProductRepositoryImpl(ProductDao()))
    }
}
