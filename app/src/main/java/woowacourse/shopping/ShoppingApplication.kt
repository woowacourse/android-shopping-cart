package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.database.DataBase
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.data.local.repository.RecentlyViewedProductRepository

class ShoppingApplication: Application() {
    val database by lazy { DataBase.getDatabase(this) }

    val purchaseProductsRepository by lazy {
        PurchaseProductsRepository(database.purchaseProductsDao())
    }

    val recentlyViewedProductRepository by lazy {
        RecentlyViewedProductRepository(database.recentlyViewedProductDao())
    }
}