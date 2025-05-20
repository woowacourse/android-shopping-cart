package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.GoodsDataBase
import woowacourse.shopping.data.ShoppingDataBase

class ShoppingApplication : Application() {
    val goodsRepository by lazy { GoodsDataBase }
    val shoppingRepository by lazy { ShoppingDataBase }
}
