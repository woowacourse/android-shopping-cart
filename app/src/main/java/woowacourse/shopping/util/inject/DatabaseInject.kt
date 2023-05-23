package woowacourse.shopping.util.inject

import android.content.Context
import woowacourse.shopping.data.database.ShoppingDatabase

fun createShoppingDatabase(context: Context): ShoppingDatabase =
    ShoppingDatabase(context)
