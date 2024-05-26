package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.datasource.history.LocalHistoryDataSource
import woowacourse.shopping.data.datasource.order.LocalOrderDataSource
import woowacourse.shopping.data.db.AppDatabase

class ShoppingApplication : Application() {
    val db by lazy { AppDatabase.getDatabase(this) }
    val localHistoryDataSource by lazy { LocalHistoryDataSource(db.historyDao()) }
    val localOrderDataSource by lazy { LocalOrderDataSource(db.orderDao()) }
}
