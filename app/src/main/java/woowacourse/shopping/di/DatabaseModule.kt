package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.RecentlyProductDao
import woowacourse.shopping.data.database.CartDatabase

object DatabaseModule {
    private lateinit var appContext: Context
    private var database: CartDatabase? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun provideDatabase(): CartDatabase = CartDatabase.getInstance(appContext).also { database = it }

    fun provideCartDao(): CartDao = provideDatabase().cartDao()

    fun provideRecentProductDao(): RecentlyProductDao = provideDatabase().recentlyProductDao()
}
