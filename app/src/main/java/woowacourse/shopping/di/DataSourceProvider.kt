package woowacourse.shopping.di

import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.CartDataSourceImpl
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductDataSourceImpl
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.data.dummyProducts

object DataSourceProvider {
    val productDataSource: ProductDataSource by lazy { initProductDataSource() }
    val cartDataSource: CartDataSource by lazy { initCartDataSource() }

    private fun initProductDataSource(): ProductDataSource = ProductDataSourceImpl(dummyProducts)

    private fun initCartDataSource(): CartDataSource {
        val database = ShoppingDatabase.getDatabase(ShoppingApplication.instance)
        val cartDao = database.cartDao()
        return CartDataSourceImpl(cartDao)
    }
}
