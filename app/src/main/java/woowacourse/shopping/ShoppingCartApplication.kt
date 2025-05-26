package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.DatabaseProvider
import woowacourse.shopping.data.repository.CartItemRepositoryImpl
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.HistoryRepositoryImpl

class ShoppingCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val database = DatabaseProvider.provideDatabase(this)
        CartRepositoryImpl.initialize(database.cartDao())
        CartItemRepositoryImpl.initialize(database.cartItemDao(), database.cartDao())
        HistoryRepositoryImpl.initialize(database.historyDao(),database.cartDao())
    }
}
