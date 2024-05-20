package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.DummyShoppingItemsDataSource
import woowacourse.shopping.data.ShoppingItemsDataSource

class ShoppingApplication : Application() {
    val cartDatabase: CartDatabase by lazy { CartDatabase.getInstance(this) }
    val shoppingDataSource: ShoppingItemsDataSource by lazy { DummyShoppingItemsDataSource.getInstance() }
}
