package woowacourse.shopping.application

import android.app.Application
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.cart.repository.CartRepositoryImpl
import woowacourse.shopping.data.history.repository.HistoryRepositoryImpl
import woowacourse.shopping.feature.cart.ViewModelFactory
import woowacourse.shopping.feature.goods.GoodsViewModel

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingDatabase.getDatabase(applicationContext) }

    val shoppingFactory by lazy {
        ViewModelFactory {
            GoodsViewModel(
                CartRepositoryImpl(database.cartDao()),
                HistoryRepositoryImpl(database.historyDao()),
            )
        }
    }
}
