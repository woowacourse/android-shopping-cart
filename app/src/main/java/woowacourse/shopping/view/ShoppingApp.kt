package woowacourse.shopping.view

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.ShoppingDatabase.Companion.getDatabase

class ShoppingApp : Application() {
    val shoppingDatabase: ShoppingDatabase by lazy { getDatabase(applicationContext) }
}
