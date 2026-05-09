package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.RecentProductEntity

@Database(
    entities = [CartEntity::class, RecentProductEntity::class],
    version = 2,
)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getDatabase(context: Context): CartDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            CartDatabase::class.java,
                            "cart_database",
                        ).fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance
            }
    }
}
