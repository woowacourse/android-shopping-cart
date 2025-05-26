package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.RecentlyProductDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.RecentlyViewedProduct

@Database(entities = [CartEntity::class, RecentlyViewedProduct::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentlyProductDao(): RecentlyProductDao

    companion object {
        private const val DB_NAME = "cart"

        @Volatile
        private var instance: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase =
            instance ?: synchronized(this) {
                instance ?: createDatabase(context).also { instance = it }
            }

        private fun createDatabase(context: Context): CartDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    DB_NAME,
                ).build()
    }
}
