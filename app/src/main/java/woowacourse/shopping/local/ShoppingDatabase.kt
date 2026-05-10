package woowacourse.shopping.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.local.cart.CartItemDao
import woowacourse.shopping.local.cart.CartItemEntity
import woowacourse.shopping.local.recent.RecentProductDao
import woowacourse.shopping.local.recent.RecentProductEntity

@Database(
    entities = [CartItemEntity::class, RecentProductEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null

        fun getInstance(context: Context): ShoppingDatabase =
            instance ?: synchronized(this) {
                instance
                    ?: Room
                        .databaseBuilder(
                            context.applicationContext,
                            ShoppingDatabase::class.java,
                            "shopping.db",
                        ).build()
                        .also { instance = it }
            }
    }
}
