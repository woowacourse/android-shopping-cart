package woowacourse.shopping.application

import android.app.Application
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.cart.repository.CartRepositoryImpl
import woowacourse.shopping.data.history.repository.HistoryRepositoryImpl
import woowacourse.shopping.feature.cart.CartViewModel
import woowacourse.shopping.feature.goods.GoodsViewModel
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsViewModel
import woowacourse.shopping.util.ViewModelFactory

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingDatabase.getDatabase(applicationContext) }

    val goodsFactory by lazy {
        ViewModelFactory {
            GoodsViewModel(
                CartRepositoryImpl(database.cartDao()),
                HistoryRepositoryImpl(database.historyDao()),
            )
        }
    }

    val goodsDetailsFactory by lazy {
        ViewModelFactory {
            GoodsDetailsViewModel(
                CartRepositoryImpl(database.cartDao()),
                HistoryRepositoryImpl(database.historyDao()),
            )
        }
    }

    val cartFactory by lazy {
        ViewModelFactory {
            CartViewModel(
                CartRepositoryImpl(database.cartDao()),
            )
        }
    }
}
