package woowacourse.shopping.data.cart

import android.content.Context
import woowacourse.shopping.data.common.ioExecutor
import woowacourse.shopping.local.ShoppingDatabase

object CartDataSourceInjector {
    private var instance: CartDataSource? = null

    fun cartDataSource(context: Context): CartDataSource =
        instance ?: synchronized(this) {
            instance ?: DefaultCartDataSource(
                ioExecutor,
                ShoppingDatabase.instance(context).cartDao(),
            )
        }.also { instance = it }
}
