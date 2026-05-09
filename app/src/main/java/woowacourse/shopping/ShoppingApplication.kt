package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.DataBase
import woowacourse.shopping.data.repository.PurchaseProductsRepository

class ShoppingApplication: Application() {
    val database by lazy { DataBase.getDatabase(this) }
    val purchaseProductsRepository by lazy { PurchaseProductsRepository(database.purchaseProductsDao()) }
}