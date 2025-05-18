package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.database.ShoppingDatabase

object DatabaseModule {
    fun provideDatabase(context: Context): ShoppingDatabase = ShoppingDatabase.getInstance(context)
}
