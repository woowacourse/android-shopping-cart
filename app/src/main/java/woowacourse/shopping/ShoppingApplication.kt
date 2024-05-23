package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.CartDatabase
import woowacourse.shopping.data.database.ProductDatabase

class ShoppingApplication : Application() {
    val shoppingDatabase: ProductDatabase by lazy { ProductDatabase.getInstance(this) }
    val cartDatabase: CartDatabase by lazy { CartDatabase.getInstance(this) }
}
