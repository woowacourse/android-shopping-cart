package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.CartDatabase
import woowacourse.shopping.data.database.ProductDatabase
import woowacourse.shopping.data.database.RecentlyProductDatabase

class ShoppingApplication : Application() {
    val shoppingDatabase: ProductDatabase by lazy { ProductDatabase.getInstance(this) }
    val cartDatabase: CartDatabase by lazy { CartDatabase.getInstance(this) }
    val recentlyProductDatabase: RecentlyProductDatabase by lazy { RecentlyProductDatabase.getInstance(this) }
}
