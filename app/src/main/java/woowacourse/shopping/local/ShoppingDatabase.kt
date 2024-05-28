package woowacourse.shopping.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.local.converter.ShoppingConverters
import woowacourse.shopping.local.dao.CartDao
import woowacourse.shopping.local.dao.RecentProductDao
import woowacourse.shopping.local.entity.CartEntity
import woowacourse.shopping.local.entity.RecentProductEntity

@Database(
    entities = [CartEntity::class, RecentProductEntity::class],
    version = 1,
)
@TypeConverters(ShoppingConverters::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        private const val DATABASE_NAME = "shopping.db"

        @Volatile
        private var instance: ShoppingDatabase? = null

        fun instance(context: Context): ShoppingDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    ShoppingDatabase::class.java,
                    DATABASE_NAME,
                ).build().also { instance = it }
            }
        }
    }
}
