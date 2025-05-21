package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.GoodsDataBase
import woowacourse.shopping.data.shopping.ShoppingDatabase
import woowacourse.shopping.data.shopping.ShoppingRepositoryImpl

class ShoppingApplication : Application() {
    val goodsRepository by lazy { GoodsDataBase }
    val shoppingRepository by lazy {
        ShoppingRepositoryImpl(
            ShoppingDatabase.getDatabase(this).shoppingDao(),
        )
    }
}
