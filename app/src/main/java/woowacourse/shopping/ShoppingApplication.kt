package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.CartDatabase

class ShoppingApplication : Application() {
    val database: CartDatabase by lazy { CartDatabase.getInstance(this) }
}
