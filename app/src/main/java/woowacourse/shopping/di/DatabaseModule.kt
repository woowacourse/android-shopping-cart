package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartDatabase
import woowacourse.shopping.data.db.RecentlyProductDao

object DatabaseModule {
    fun provideDatabase(context: Context): CartDatabase = CartDatabase.getInstance(context.applicationContext)

    fun provideCartDao(db: CartDatabase): CartDao = db.cartDao()

    fun provideRecentProductDao(db: CartDatabase): RecentlyProductDao = db.recentlyProductDao()
}
