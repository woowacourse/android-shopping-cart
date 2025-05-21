package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.GoodsRepositoryImpl
import woowacourse.shopping.data.shopping.ShoppingDatabase
import woowacourse.shopping.data.shopping.ShoppingRepositoryImpl

class ShoppingApplication : Application() {
    val goodsRepository by lazy { GoodsRepositoryImpl }
    val shoppingRepository by lazy {
        ShoppingRepositoryImpl(
            ShoppingDatabase.getDatabase(this).shoppingDao(),
        )
    }
}
